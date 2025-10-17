package eu.tutorials.koperasi_simpan_pinjam.data.API
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import java.util.Date
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

data class RegisterRequest(
    val email: String,
    val password: String,
)

data class LoginRequest(
    val email: String,
    val password: String,
)

data class UserAmount(
    val CountUser: Int,
    val message : String
)

data class User(
    val _id: String? = null,
    val name: String? = null,
    val age: Int? = null,
    val email: String? = null,
    val createdAt: Date? = null,
    val role: String? = null,
    val password: String? = null,
    val gender: String? = null,
    val date_birth: Date? = null,
    val member_status: Boolean = false
)

data class GetUserRequest(
    val users: List<User>,
    val message: String
)

interface ApiService {
    @POST("/users/register")
    suspend fun registerUser(@Body user: RegisterRequest): Response<ResponseMessage>

    @POST("/users/login")
    suspend fun loginUser(@Body user: LoginRequest): Response<ResponseMessage>

    @GET("/users/count")
    suspend fun  CountUsers(): Response<UserAmount>

    @GET("/users")
    suspend fun getAllUsers(): Response<GetUserRequest>

    @PUT("/users/{id}")
    suspend fun updateUser(
        @Path("id") id: String?,
        @Body user: User
    ): Response<User>

    @DELETE("/users/{id}")
    suspend fun deleteUser(
        @Path("id") id: String?
    ): Response<ResponseMessage>
}

data class ResponseMessage(
    val message: String,
    val isAdmin: Boolean
)