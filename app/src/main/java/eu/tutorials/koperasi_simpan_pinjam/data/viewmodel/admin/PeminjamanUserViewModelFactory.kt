package eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eu.tutorials.koperasi_simpan_pinjam.data.repository.admin.UserRepositoryPeminjaman

@Suppress("UNCHECKED_CAST")
class PeminjamanUserViewModelFactory(
    private val repository: UserRepositoryPeminjaman
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PeminjamanUserViewModel::class.java)) {
            return PeminjamanUserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}