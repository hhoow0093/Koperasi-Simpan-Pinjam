package eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.tutorials.koperasi_simpan_pinjam.data.API.PembayaranLoanDto
import eu.tutorials.koperasi_simpan_pinjam.data.repository.admin.PembayaranLoanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PembayaranLoanViewModel(
    private val repository: PembayaranLoanRepository
) : ViewModel() {

    private val _pembayaranList = MutableStateFlow<List<PembayaranLoanDto>>(emptyList())
    val pembayaranList = _pembayaranList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun fetchPembayaranByLoanId(loanId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.getPembayaranByLoanId(loanId)
                    .onSuccess {
                        _pembayaranList.value = it
                    }
                    .onFailure {
                        _error.value = it.message
                    }
            } finally {
                _isLoading.value = false
            }
        }
    }
}
