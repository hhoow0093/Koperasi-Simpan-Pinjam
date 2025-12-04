package eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.Nasabah

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DashboardNasabahViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DashboardViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}