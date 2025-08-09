package com.shiftx.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shiftx.data.repository.EmployeeRepository
import com.shiftx.datastore.SessionStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeeLoginViewModel @Inject constructor(
    private val employeeRepository: EmployeeRepository,
    private val sessionStore: SessionStore
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun login(employeeId: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            val ok = employeeRepository.validateCredentials(employeeId, password)
            if (ok) {
                sessionStore.setLoggedInEmployee(employeeId)
                onSuccess()
            } else {
                _error.value = "Invalid ID or password"
            }
            _loading.value = false
        }
    }
}