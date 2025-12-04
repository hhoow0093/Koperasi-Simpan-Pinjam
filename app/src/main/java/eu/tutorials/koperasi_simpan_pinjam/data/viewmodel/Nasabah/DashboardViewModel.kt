package eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.Nasabah

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.tutorials.koperasi_simpan_pinjam.data.API.ItemHistori
import eu.tutorials.koperasi_simpan_pinjam.data.API.PengajuanPinjaman
import eu.tutorials.koperasi_simpan_pinjam.data.API.PinjamanAktif
import eu.tutorials.koperasi_simpan_pinjam.data.API.PostPinjamanRequest
import eu.tutorials.koperasi_simpan_pinjam.data.API.PostSimpananRequest
import eu.tutorials.koperasi_simpan_pinjam.data.API.RetrofitClient
import eu.tutorials.koperasi_simpan_pinjam.data.API.TransaksiSimpanan
import eu.tutorials.koperasi_simpan_pinjam.data.API.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DashboardViewModel(context: Context) : ViewModel() {
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

    //FETCH FUNC
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

    //CRUD (POST)
    fun postSimpanan(userId: String, type: String, amount: Double) {
        viewModelScope.launch {
            try {
                val request = PostSimpananRequest(userId, type, amount)
                val response = api.addSimpanan(request)
                if (response.isSuccessful) {
                    // Refresh data to update UI automatically
                    loadAllData(userId)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun postPinjaman(userId: String, amount: Double, tenor: Int) {
        viewModelScope.launch {
            try {
                val request = PostPinjamanRequest(userId, amount, tenor)
                val response = api.ajukanPinjaman(request)
                if (response.isSuccessful) {
                    loadAllData(userId) // Refresh list
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}