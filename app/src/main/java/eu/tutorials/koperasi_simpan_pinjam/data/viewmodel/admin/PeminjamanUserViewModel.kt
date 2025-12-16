// File: PeminjamanUserViewModel.kt
package eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.tutorials.koperasi_simpan_pinjam.data.API.Pinjaman
import eu.tutorials.koperasi_simpan_pinjam.data.repository.admin.UserRepositoryPeminjaman
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PeminjamanUserViewModel(
    private val repository: UserRepositoryPeminjaman
) : ViewModel() {
    private val _pinjamanList = MutableStateFlow<List<Pinjaman>>(emptyList())
    val pinjamanList: StateFlow<List<Pinjaman>> = _pinjamanList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _approveState = MutableStateFlow(false)
    val approveState = _approveState.asStateFlow()

    fun fetchPinjamanUser(userId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val pinjaman = repository.getPinjamanUser(userId)
                _pinjamanList.value = pinjaman
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Unknown error"
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun approveLoan(
        loanId: String,
        bunga: Int,
        denda: Int,
        userId: String
    ) {
        viewModelScope.launch {
            repository.approveLoan(loanId, bunga, denda)
                .onSuccess {
                    _approveState.value = true
                    fetchPinjamanUser(userId) // refresh list
                }
                .onFailure {
                    _approveState.value = false
                }
        }
    }

    fun rejectLoan(
        loanId: String,
        reason: String,
        userId: String
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.rejectLoan(loanId, reason)
                result.onSuccess {
                    fetchPinjamanUser(userId) // 🔄 refresh list
                }
                result.onFailure {
                    _errorMessage.value = it.message
                }
            } finally {
                _isLoading.value = false
            }
        }
    }
}