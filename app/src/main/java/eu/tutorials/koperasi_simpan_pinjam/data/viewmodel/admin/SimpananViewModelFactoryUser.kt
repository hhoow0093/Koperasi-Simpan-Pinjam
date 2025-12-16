package eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eu.tutorials.koperasi_simpan_pinjam.data.repository.admin.UserRepositorySimpanan

class SimpananViewModelFactoryUser (
    private val repository: UserRepositorySimpanan
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SimpananViewModelUser::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SimpananViewModelUser(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}