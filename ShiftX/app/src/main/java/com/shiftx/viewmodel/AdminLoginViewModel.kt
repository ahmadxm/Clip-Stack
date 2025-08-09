package com.shiftx.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shiftx.datastore.SessionStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminLoginViewModel @Inject constructor(
    private val sessionStore: SessionStore
) : ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val adminEmail = "admin@shiftx.local"
    private val adminPassword = "admin123"

    fun login(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            if (email == adminEmail && password == adminPassword) {
                sessionStore.setAdminLoggedIn(true)
                onSuccess()
            } else {
                _error.value = "Invalid admin credentials"
            }
            _loading.value = false
        }
    }
}