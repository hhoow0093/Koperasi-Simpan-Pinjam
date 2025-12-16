package eu.tutorials.koperasi_simpan_pinjam.data.API
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import java.util.Date
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import okhttp3.MultipartBody
import retrofit2.http.Part
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.http.Multipart


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
    @SerializedName("_id")
    val _id: String? = null,
    val name: String? = null,
    val age: Int? = null,
    val email: String? = null,
    val createdAt: Date? = null,
    val role: String? = null,
    val password: String? = null,
    val gender: String? = null,
    val date_birth: Date? = null,
    val member_status: Boolean = false,
    val profile_image: String? = null
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
    val tipe: TipeTransaksi,
    val buktiImageId: String,
    val buktiImageUrl: String
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
    val id: String,
    val size: Int,
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

data class Pinjaman(
    val dendaKeterlambatan: Double,
    val _id : String,
    val userId:String,
    val jumlah: Double,
    val tenor: Int,
    val status: String,
    val bunga: Double,
    val totalCicilanPerBulan: Double,
    val sisaAngsuran: Int,
    val totalAngsuran: Int,
    val createdAt: Date,
)

data class responseListPinjamanNasabah(
    val nasabahLoans: List<Pinjaman>,
    val error: String,
    val message: String
)

data class PembayaranLoanDto(
    val _id: String,
    val loanId: String,
    val userId: String,
    val BuktiImagePembayaranDalamPinjamanId: String?,
    val createdAt: String
)

data class MLResult(
    @SerializedName("default_prediction")
    val defaultPrediction: Int,

    @SerializedName("risk_probability")
    val riskProbability: Double
)

data class MLApiResponse(
    val success: Boolean,

    @SerializedName("ml_result")
    val mlResult: MLResult
)
data class MLRequest(
    @SerializedName("age")
    val age: Float,

    @SerializedName("income_per_month")
    val incomePerMonth: Float,

    @SerializedName("credit_point")
    val creditPoint: Float,

    @SerializedName("loan_occurrences_per_month")
    val loanOccurrencesPerMonth: Float
)

interface ApiService {
    @POST("/prediksi-machine-learning")
    suspend fun predictDefault(
        @Body request: MLRequest
    ): Response<MLApiResponse>


    @GET("pembayaran-loan/by-loan/{loanId}")
    suspend fun getPembayaranLoanByLoanId(
        @Path("loanId") loanId: String
    ): Response<List<PembayaranLoanDto>>

    @POST("/users/register")
    suspend fun registerUser(@Body user: RegisterRequest): Response<ResponseMessage>

    @POST("/users/login")
    suspend fun loginUser(@Body user: LoginRequest): Response<ResponseMessage>

    @GET("/users/count")
    suspend fun  CountUsers(): Response<UserAmount>

    @GET("/users")
    suspend fun getAllUsers(): Response<GetUserRequest>

    @GET("/user/{id}")
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



    //PINJAMAN
    //dapet pinjaman yang sedang berjalan

    @Multipart
    @POST("/pinjaman/bayarAngsuran/{userId}/{pinjamanId}")
    suspend fun bayarAngsuran(
        @Path("userId") userId: String,
        @Path("pinjamanId") pinjamanId: String,
        @Part image: MultipartBody.Part
    ): Response<MyResponse>

    @GET("/pinjaman/list/{userId}")
    suspend fun getPinjamanList(@Path("userId") userId: String): Response<responseListPinjamanNasabah>

    @GET("/pinjaman/active/{userId}")
    suspend fun getPinjamanAktif(@Path("userId") userId: String): Response<PinjamanAktif>

    //list histori pinjaman + status pinjaman
    @GET("/pinjaman/history/{userId}")
    suspend fun getPengajuanHistory(@Path("userId") userId: String): Response<List<PengajuanPinjaman>>

    //apply pinjaman baru
    @POST("/pinjaman/apply")
    suspend fun ajukanPinjaman(@Body request: PostPinjamanRequest): Response<ResponseMessage>

    @Multipart
    @POST("/user/{userId}/upload-profile-image")
    suspend fun uploadProfileImage(
        @Path("userId") userId: String,
        @Part image: MultipartBody.Part
    ): Response<UploadResponse>

    @GET("/user/{userId}/profile-image")
    suspend fun getProfileImage(
        @Path("userId") userId: String
    ): Response<ResponseBody>
    //membuat simpanan baru
    @Multipart
    @POST("/simpanan")
    suspend fun addSimpanan(
        @Part("userId") userId: RequestBody,
        @Part("type") type: RequestBody,
        @Part("amount") amount: RequestBody,
        @Part simpananImage: MultipartBody.Part
    ): Response<ResponseMessage>

    data class UploadResponse(
        val message: String,
    )

    @DELETE("/simpanan/{simpananId}")
    suspend fun deleteSimpananFromAdmin(
        @Path("simpananId") simpananId: String?
    ): Response<ResponseMessageAfterDeleteSimpananFromAdmin>

    @PUT("pinjaman/approve/{loanId}")
    suspend fun approveLoan(
        @Path("loanId") loanId: String,
        @Body body: ApproveLoanRequest
    ): Response<LoanResponseApprove>

    @PUT("pinjaman/reject/{loanId}")
    suspend fun rejectLoan(
        @Path("loanId") loanId: String,
        @Body body: RejectLoanRequest
    ): Response<MyResponse>



    //HISTORI TRANSAKSI
    @GET("/transaksi/all/{userId}")
    suspend fun getAllHistori(@Path("userId") userId: String): Response<List<ItemHistori>>
}

data class LoanResponseApprove(
    val message: String,
    val loan: Pinjaman
)

data class RejectLoanRequest(
    val reason: String,
)

data class ApproveLoanRequest(
    val bungaPersen: Int,
    val dendaPersen: Int
)
data class ResponseMessage(
    val message: String,
    val isAdmin: Boolean,
    val user_id: String
)

data class ResponseMessageAfterDeleteSimpananFromAdmin(
    val success: Boolean,
    val message: String
)

data class MyResponse(
    val message: String
)