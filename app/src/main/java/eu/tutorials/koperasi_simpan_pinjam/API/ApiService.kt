package eu.tutorials.koperasi_simpan_pinjam.API
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.Date
import java.util.UUID

data class RegisterRequest(
    val email: String,
    val password: String,
    val name: String? = null,
    val gender: String? = null,
    val date_birth: Date? = null
)

data class LoginRequest(
    val email: String,
    val password: String,
)

data class User(
    val name: String,
    val email: String,
    val password: String,
    val id: UUID,
    val age: Int,
    val createdAt: Date,
    val role: String,
    val gender: String,
    val date_birth: Date
)

interface ApiService {
    @POST("/users/register")
    suspend fun registerUser(@Body user: RegisterRequest): retrofit2.Response<ResponseMessage>

    @POST("/users/login")
    suspend fun loginUser(@Body user: LoginRequest): retrofit2.Response<ResponseMessage>
}

data class ResponseMessage(
    val message: String
)