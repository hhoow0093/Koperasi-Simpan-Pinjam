package eu.tutorials.koperasi_simpan_pinjam.data.repository.admin

import eu.tutorials.koperasi_simpan_pinjam.data.API.ApiService
import eu.tutorials.koperasi_simpan_pinjam.data.API.ApproveLoanRequest
import eu.tutorials.koperasi_simpan_pinjam.data.API.Pinjaman
import eu.tutorials.koperasi_simpan_pinjam.data.API.RejectLoanRequest
import eu.tutorials.koperasi_simpan_pinjam.data.API.responseListPinjamanNasabah

class UserRepositoryPeminjaman(private val api: ApiService) {
    suspend fun getPinjamanUser(userId: String): List<Pinjaman>{
        val response = api.getPinjamanList(userId = userId)
        if(response.isSuccessful){
            val body = response.body()
            return body?.nasabahLoans ?: emptyList()
        }else{
            throw Exception("Error fetching user Pinjaman: ${response.errorBody()?.string()}")
        }
    }
    suspend fun approveLoan(
        loanId: String,
        bunga: Int,
        denda: Int
    ): Result<Unit> {
        return try {
            val response = api.approveLoan(
                loanId,
                ApproveLoanRequest(bunga, denda)
            )
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Approval failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun rejectLoan(
        loanId: String,
        reason: String
    ): Result<Unit> {
        return try {
            val response = api.rejectLoan(
                loanId,
                RejectLoanRequest(reason)
            )
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Reject gagal"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}