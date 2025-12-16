package eu.tutorials.koperasi_simpan_pinjam.data.repository.admin

import eu.tutorials.koperasi_simpan_pinjam.data.API.ApiService
import eu.tutorials.koperasi_simpan_pinjam.data.API.PembayaranLoanDto

class PembayaranLoanRepository(
    private val api: ApiService
) {

    suspend fun getPembayaranByLoanId(
        loanId: String
    ): Result<List<PembayaranLoanDto>> {
        return try {
            val response = api.getPembayaranLoanByLoanId(loanId)
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Gagal mengambil pembayaran"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
