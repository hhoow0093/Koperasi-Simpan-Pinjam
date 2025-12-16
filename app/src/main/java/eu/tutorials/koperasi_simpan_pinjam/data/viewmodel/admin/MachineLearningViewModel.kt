package eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.admin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.tutorials.koperasi_simpan_pinjam.data.API.MLResult
import eu.tutorials.koperasi_simpan_pinjam.data.repository.admin.MLRepository
import kotlinx.coroutines.launch

class MachineLearningViewModel(
    private val repository: MLRepository
) : ViewModel() {

    var isLoading by mutableStateOf(false)
        private set

    var result by mutableStateOf<MLResult?>(null)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set


    fun predict(
        age: Float,
        income: Float,
        credit: Float,
        loanFreq: Float
    ) {
        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null

                result = repository.predictDefault(
                    age, income, credit, loanFreq
                )

            } catch (e: Exception) {
                errorMessage = e.message
            } finally {
                isLoading = false
            }
        }
    }
}
