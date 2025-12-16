package eu.tutorials.koperasi_simpan_pinjam.data.repository.admin

import eu.tutorials.koperasi_simpan_pinjam.data.API.ApiService
import eu.tutorials.koperasi_simpan_pinjam.data.API.ResponseMessageAfterDeleteSimpananFromAdmin
import eu.tutorials.koperasi_simpan_pinjam.data.API.TransaksiSimpanan
import eu.tutorials.koperasi_simpan_pinjam.data.API.User
import retrofit2.Response


class UserRepositorySimpanan(private val api: ApiService) {
    suspend fun getSimpananUser(userId: String): List<TransaksiSimpanan>{
        val response = api.getSimpananData(userId)
        if(response.isSuccessful){
            val body = response.body()
            return body?.riwayatTransaksi?: emptyList()
        }else{
            throw Exception("Error fetching user Saving: ${response.errorBody()?.string()}")
        }
    }
    suspend fun getUserInformationName(userId:String): String{
        val response = api.getUserById(userId)
        if(response.isSuccessful){
            val body = response.body()
            return body?.name?: ""
        }else{
            throw Exception("Error fetching user name: ${response.errorBody()?.string()}")
        }
    }
    suspend fun deleteSimpanan(SimpananId: String): Response<ResponseMessageAfterDeleteSimpananFromAdmin>{
        return api.deleteSimpananFromAdmin(simpananId = SimpananId)
    }
}