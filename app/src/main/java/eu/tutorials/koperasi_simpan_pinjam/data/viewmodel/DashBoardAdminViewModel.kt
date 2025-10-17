package eu.tutorials.koperasi_simpan_pinjam.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.tutorials.koperasi_simpan_pinjam.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DashBoardAdminViewModel(private val userRepository: UserRepository): ViewModel() {
    private val _userCount = MutableStateFlow(value= 0)
    val userCount: StateFlow<Int> = _userCount

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun loadUserCount(){
        viewModelScope.launch {
            try{
                val count = userRepository.fetchUserCount()
                _userCount.value = count

            }catch (e: Exception){
                _errorMessage.value = e.message
            }
        }
    }

}