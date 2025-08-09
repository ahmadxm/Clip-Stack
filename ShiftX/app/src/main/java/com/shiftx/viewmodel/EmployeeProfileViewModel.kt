package com.shiftx.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shiftx.data.db.entities.Employee
import com.shiftx.data.db.entities.Shift
import com.shiftx.data.repository.EmployeeRepository
import com.shiftx.data.repository.ShiftRepository
import com.shiftx.datastore.SessionStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class EmployeeProfileViewModel @Inject constructor(
    private val sessionStore: SessionStore,
    private val employeeRepository: EmployeeRepository,
    private val shiftRepository: ShiftRepository
) : ViewModel() {
    private val formatter = DateTimeFormatter.ISO_DATE

    val employee: StateFlow<Employee?> = sessionStore.loggedInEmployeeIdFlow.flatMapLatest { id ->
        employeeRepository.getEmployeeByIdFlow(id ?: "")
    }.stateIn(viewModelScope, SharingStarted.Eagerly, null)

    val todayShift: StateFlow<Shift?> = sessionStore.loggedInEmployeeIdFlow.flatMapLatest { id ->
        val today = LocalDate.now().format(formatter)
        shiftRepository.getShiftsForEmployeeBetween(id ?: "", today, today)
    }.combine(employee) { shifts, _ -> shifts.firstOrNull() }
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)
}