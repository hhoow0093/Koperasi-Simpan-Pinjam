package eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eu.tutorials.koperasi_simpan_pinjam.data.repository.admin.MLRepository

class MachineLearningViewModelFactory(
    private val repository: MLRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MachineLearningViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MachineLearningViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
