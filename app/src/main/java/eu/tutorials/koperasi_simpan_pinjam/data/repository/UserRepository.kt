package eu.tutorials.koperasi_simpan_pinjam.data.repository

import eu.tutorials.koperasi_simpan_pinjam.data.API.ApiService
import eu.tutorials.koperasi_simpan_pinjam.data.API.ResponseMessage
import eu.tutorials.koperasi_simpan_pinjam.data.API.User
import retrofit2.Response

class UserRepository(private val api: ApiService){
    suspend fun fetchUserCount(): Int {
        val response =  api.CountUsers()
        if(response.isSuccessful){
            val body = response.body()
            return body?.CountUser?: 0
        }else{
            throw Exception("Error fetching user count: ${response.errorBody()?.string()}")
        }
    }

    suspend fun getAllUsers(): List<User> {
        val response = api.getAllUsers()
        if(response.isSuccessful){
            val body = response.body()
            return body?.users?: emptyList()
        }else{
            throw Exception( "Error getting users: ${response.errorBody()?.string()}")
        }
    }
    suspend fun updateUser(user: User): Response<User> {
        return api.updateUser(user._id, user)
    }

    suspend fun deleteUser(user: User): Response<ResponseMessage>{
        return api.deleteUser(user._id)
    }
}