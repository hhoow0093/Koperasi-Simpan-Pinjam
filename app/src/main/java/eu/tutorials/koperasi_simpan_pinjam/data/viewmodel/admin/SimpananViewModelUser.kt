package eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.admin

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.tutorials.koperasi_simpan_pinjam.data.API.TransaksiSimpanan
import eu.tutorials.koperasi_simpan_pinjam.data.API.User
import eu.tutorials.koperasi_simpan_pinjam.data.repository.admin.UserRepositorySimpanan
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
private val indoFormatter =
    DateTimeFormatter.ofPattern("dd MMM yyyy", Locale("id", "ID"))

@RequiresApi(Build.VERSION_CODES.O)
fun parseMonth(tanggal: String): String {
    return try {
        val date = LocalDate.parse(tanggal, indoFormatter)
        date.month.name.take(3).capitalize() // JAN, FEB, MAR
    } catch (e: Exception) {
        "Unknown"
    }
}

class SimpananViewModelUser(private val repository: UserRepositorySimpanan): ViewModel() {

    private val _monthlyStats = MutableStateFlow<Map<String, Double>>(emptyMap())
    val monthlyStats: StateFlow<Map<String, Double>> = _monthlyStats
    private val _TransaksiSimpananList = MutableStateFlow<List<TransaksiSimpanan>>(emptyList())
    val TransaksiSimpananList: StateFlow<List<TransaksiSimpanan>> = _TransaksiSimpananList

    private val _name = MutableStateFlow<String?>(null)
    val name : StateFlow<String?> = _name

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchAllTransaksiSimpanan(userId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val Transaksi = repository.getSimpananUser(userId)
                _TransaksiSimpananList.value = Transaksi

                _monthlyStats.value = Transaksi
                    .groupBy { parseMonth(it.tanggal) }
                    .mapValues { (_, list) ->
                        list.sumOf { it.jumlah }
                    }
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun getUserName(userId:String){
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try{
                val Myname = repository.getUserInformationName(userId)
                _name.value = Myname
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun DeleteSimpanan(userId: String, simpananId: String){
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try{
                val response = repository.deleteSimpanan(simpananId)
                if(response.isSuccessful){
                    fetchAllTransaksiSimpanan(userId)
                }else{
                    _errorMessage.value = response.errorBody()?.string() ?: "delete simpanan failure"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

}