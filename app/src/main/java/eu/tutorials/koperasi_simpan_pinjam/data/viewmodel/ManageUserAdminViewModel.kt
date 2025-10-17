package eu.tutorials.koperasi_simpan_pinjam.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import eu.tutorials.koperasi_simpan_pinjam.data.API.User
import eu.tutorials.koperasi_simpan_pinjam.data.repository.UserRepository

class ManageUserAdminViewModel(private val repository: UserRepository) : ViewModel() {
    private val _userList = MutableStateFlow<List<User>>(emptyList())
    val userList: StateFlow<List<User>> = _userList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchAllUsers() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val users = repository.getAllUsers()
                _userList.value = users
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun updateUser(updatedUser: User) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = repository.updateUser(updatedUser)
                if (response.isSuccessful) {
                    fetchAllUsers()
                } else {
                    _errorMessage.value = response.errorBody()?.string() ?: "Unknown error"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteUser(user: User){
        viewModelScope.launch {
            try{
                _isLoading.value = true
                val response = repository.deleteUser(user)
                if(response.isSuccessful){
                    fetchAllUsers()
                }else{
                    _errorMessage.value = response.errorBody()?.string() ?: "Unknown error"
                }
            }catch (e: Exception){
                _errorMessage.value = e.message

            }finally {
                _isLoading.value = false
            }
        }

    }
}
