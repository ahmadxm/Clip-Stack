package com.shiftx.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shiftx.datastore.SessionStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val store: SessionStore
) : ViewModel() {
    val theme: StateFlow<String> = store.themeFlow.stateIn(viewModelScope, SharingStarted.Eagerly, "system")
    val timeFormat: StateFlow<String> = store.timeFormatFlow.stateIn(viewModelScope, SharingStarted.Eagerly, "24")
    val shiftDisplay: StateFlow<String> = store.shiftDisplayFlow.stateIn(viewModelScope, SharingStarted.Eagerly, "full")

    fun setTheme(v: String) { viewModelScope.launch { store.setTheme(v) } }
    fun setTimeFormat(v: String) { viewModelScope.launch { store.setTimeFormat(v) } }
    fun setShiftDisplay(v: String) { viewModelScope.launch { store.setShiftDisplay(v) } }
}