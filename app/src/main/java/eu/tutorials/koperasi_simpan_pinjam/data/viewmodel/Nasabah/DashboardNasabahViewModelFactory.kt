package eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.Nasabah

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eu.tutorials.koperasi_simpan_pinjam.data.repository.user.UserNasabahRepository

class DashboardNasabahViewModelFactory(private val repository: UserNasabahRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return DashboardViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}