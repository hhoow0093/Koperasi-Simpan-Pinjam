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

//UNTUK SIMPANAN
enum class TipeTransaksi { KREDIT, DEBIT }

data class TransaksiSimpanan(
    val id: String,
    val tanggal: String,
    val keterangan: String,
    val jumlah: Double,
    val tipe: TipeTransaksi
)

data class SimpananResponse(
    val totalSaldo: Double,
    val riwayatTransaksi: List<TransaksiSimpanan>
)

data class PostSimpananRequest(
    val userId: String,
    val type: String, // "Pokok", "Wajib", etc.
    val amount: Double
)

//Untuk PINJAMAN
data class PinjamanAktif(
    val pokok: Double,
    val bunga: Double,
    val totalCicilanPerBulan: Double,
    val sisaAngsuran: Int, //dalam bulan
    val totalAngsuran: Int
)


data class PengajuanPinjaman(
    val id: String,
    val tanggal: String,
    val jumlah: Double,
    val tenor: Int,
    val status: String // "Proses", "Disetujui", "Ditolak"
)

data class PostPinjamanRequest(
    val userId: String,
    val jumlah: Double,
    val tenor: Int
)

//HISTORI TRANSAKSI
enum class TipeHistori { SIMPANAN_MASUK, SIMPANAN_KELUAR, PENGAJUAN_PINJAMAN, BAYAR_ANGSURAN, BAYAR_DENDA }

data class ItemHistori(
    val id: String,
    val tanggal: String,
    val jumlah: Double,
    val keterangan: String,
    val tipe: TipeHistori
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

    @GET("/users/{id}")
    suspend fun getUserById(
        @Path("id") id: String?
    ): Response<User>

    @PUT("/users/{id}")
    suspend fun updateUser(
        @Path("id") id: String?,
        @Body user: User
    ): Response<User>

    @DELETE("/users/{id}")
    suspend fun deleteUser(
        @Path("id") id: String?
    ): Response<ResponseMessage>

    //SIMPANAN
    //total balance dan list transaksi
    @GET("/simpanan/user/{userId}")
    suspend fun getSimpananData(@Path("userId") userId: String): Response<SimpananResponse>

    //membuat simpanan baru
    @POST("/simpanan")
    suspend fun addSimpanan(@Body request: PostSimpananRequest): Response<ResponseMessage>

    //PINJAMAN
    //dapet pinjaman yang sedang berjalan
    @GET("/pinjaman/active/{userId}")
    suspend fun getPinjamanAktif(@Path("userId") userId: String): Response<PinjamanAktif>

    //list histori pinjaman + status pinjaman
    @GET("/pinjaman/history/{userId}")
    suspend fun getPengajuanHistory(@Path("userId") userId: String): Response<List<PengajuanPinjaman>>

    //apply pinjaman baru
    @POST("/pinjaman/apply")
    suspend fun ajukanPinjaman(@Body request: PostPinjamanRequest): Response<ResponseMessage>

    //HISTORI TRANSAKSI
    @GET("/transaksi/all/{userId}")
    suspend fun getAllHistori(@Path("userId") userId: String): Response<List<ItemHistori>>
}

data class ResponseMessage(
    val message: String,
    val isAdmin: Boolean,
    val user_id: String
)