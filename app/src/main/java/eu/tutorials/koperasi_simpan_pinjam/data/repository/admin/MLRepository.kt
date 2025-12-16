package eu.tutorials.koperasi_simpan_pinjam.data.repository.admin

import eu.tutorials.koperasi_simpan_pinjam.data.API.ApiService
import eu.tutorials.koperasi_simpan_pinjam.data.API.MLRequest
import eu.tutorials.koperasi_simpan_pinjam.data.API.MLResult

class MLRepository(
    private val api: ApiService
) {
    suspend fun predictDefault(
        age: Float,
        income: Float,
        credit: Float,
        loanFreq: Float
    ): MLResult {

        val request = MLRequest(
            age = age,
            incomePerMonth = income,
            creditPoint = credit,
            loanOccurrencesPerMonth = loanFreq
        )

        val response = api.predictDefault(request)

        if (!response.isSuccessful || response.body() == null) {
            throw Exception("Prediction failed")
        }

        return response.body()!!.mlResult
    }
}
