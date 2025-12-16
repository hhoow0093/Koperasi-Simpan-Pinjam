package eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eu.tutorials.koperasi_simpan_pinjam.data.repository.admin.PembayaranLoanRepository

class PembayaranLoanViewModelFactory(
    private val repository: PembayaranLoanRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PembayaranLoanViewModel::class.java)) {
            PembayaranLoanViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
