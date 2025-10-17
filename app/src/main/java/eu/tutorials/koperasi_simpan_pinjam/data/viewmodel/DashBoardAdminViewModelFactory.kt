package eu.tutorials.koperasi_simpan_pinjam.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eu.tutorials.koperasi_simpan_pinjam.data.repository.UserRepository


class DashboardViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashBoardAdminViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DashBoardAdminViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}