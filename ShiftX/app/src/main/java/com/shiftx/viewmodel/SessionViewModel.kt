package com.shiftx.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shiftx.datastore.SessionStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val sessionStore: SessionStore
) : ViewModel() {
    val loggedInEmployeeIdState = mutableStateOf<String?>(null)
    val isAdminLoggedInState = mutableStateOf(false)

    init {
        sessionStore.loggedInEmployeeIdFlow.onEach { loggedInEmployeeIdState.value = it }.launchIn(viewModelScope)
        sessionStore.adminLoggedInFlow.onEach { isAdminLoggedInState.value = it }.launchIn(viewModelScope)
    }
}