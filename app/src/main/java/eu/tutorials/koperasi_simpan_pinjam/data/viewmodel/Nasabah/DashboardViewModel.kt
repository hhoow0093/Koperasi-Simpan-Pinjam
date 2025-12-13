package eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.Nasabah

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.tutorials.koperasi_simpan_pinjam.data.API.*
import eu.tutorials.koperasi_simpan_pinjam.data.repository.user.UserNasabahRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody

fun String.toTextBody(): RequestBody =
    this.toRequestBody("text/plain".toMediaType())

fun Double.toTextBody(): RequestBody =
    this.toString().toRequestBody("text/plain".toMediaType())

class DashboardViewModel(private val repository: UserNasabahRepository) : ViewModel() {
    private val api = RetrofitClient.instance

    //home and profile
    private val _userProfile = MutableStateFlow<User?>(null)
    val userProfile: StateFlow<User?> = _userProfile

    private val _totalSaldo = MutableStateFlow(0.0)
    val totalSaldo: StateFlow<Double> = _totalSaldo

    private val _pinjamanAktif = MutableStateFlow<PinjamanAktif?>(null)
    val pinjamanAktif: StateFlow<PinjamanAktif?> = _pinjamanAktif

    //simpanan page
    private val _transaksiSimpananList = MutableStateFlow<List<TransaksiSimpanan>>(emptyList())
    val transaksiSimpananList: StateFlow<List<TransaksiSimpanan>> = _transaksiSimpananList

    //pinjaman page
    private val _pengajuanList = MutableStateFlow<List<PengajuanPinjaman>>(emptyList())
    val pengajuanList: StateFlow<List<PengajuanPinjaman>> = _pengajuanList

    //histori page
    private val _historiList = MutableStateFlow<List<ItemHistori>>(emptyList())
    val historiList: StateFlow<List<ItemHistori>> = _historiList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // image profile nasabah
    private val _uploadResult = MutableLiveData<String>()
    val uploadResult: LiveData<String> = _uploadResult

    private val _profileImage = MutableLiveData<Bitmap>()
    val profileImage: LiveData<Bitmap> = _profileImage

    private val _uploadSuccess = MutableStateFlow(false)
    val uploadSuccess = _uploadSuccess


    fun loadAllData(userId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // 1. Get User Info
                val userRes = api.getUserById(userId)
                if (userRes.isSuccessful) _userProfile.value = userRes.body()

                // 2. Get Simpanan Data (Balance + Transactions)
                val simpananRes = api.getSimpananData(userId)
                if (simpananRes.isSuccessful) {
                    val data = simpananRes.body()
                    if (data != null) {
                        _totalSaldo.value = data.totalSaldo
                        _transaksiSimpananList.value = data.riwayatTransaksi
                    }
                }
                // 3. Get Pinjaman Data
                val pinjamanRes = api.getPinjamanAktif(userId)
                if (pinjamanRes.isSuccessful) _pinjamanAktif.value = pinjamanRes.body()

                val pengajuanRes = api.getPengajuanHistory(userId)
                if (pengajuanRes.isSuccessful) _pengajuanList.value = pengajuanRes.body() ?: emptyList()

                // 4. Get All Histori
                val historiRes = api.getAllHistori(userId)
                if (historiRes.isSuccessful) _historiList.value = historiRes.body() ?: emptyList()

            } catch (e: Exception) {
                e.printStackTrace() // Handle error
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun postSimpanan(
        userId: String,
        type: String,
        amount: Double,
        imageBytes: ByteArray
    ) {
        viewModelScope.launch {
            try {
                val imageRequest = imageBytes.toRequestBody("image/jpeg".toMediaType())

                val imagePart = MultipartBody.Part.createFormData(
                    "simpananImage",   // MUST match upload.single("simpananImage")
                    "simpanan.jpg",
                    imageRequest
                )

                val response = api.addSimpanan(
                    userId = userId.toTextBody(),
                    type = type.toTextBody(),
                    amount = amount.toTextBody(),
                    simpananImage = imagePart
                )

                if (response.isSuccessful) {
                    loadAllData(userId)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun postPinjaman(userId: String, amount: Double, /*typeOfLoans: String,*/ tenor: Int) {
        viewModelScope.launch {
            try {
                val request = PostPinjamanRequest(userId, amount, /*typeOfLoans, */tenor)
                val response = api.ajukanPinjaman(request)
                if (response.isSuccessful) {
                    loadAllData(userId) // Refresh list
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun uploadProfileImage(userId: String, bytes: ByteArray, fileName: String) {
        viewModelScope.launch {
            repository.uploadProfileImage(userId, bytes, fileName)
            _uploadSuccess.value = true
        }
    }

    fun loadProfileImage(userId: String) {
        viewModelScope.launch {
            repository.getProfileImage(userId)
                .onSuccess {
                    _profileImage.value = it
                }
                .onFailure {
                    _uploadResult.value = it.message
                }
        }
    }
}
