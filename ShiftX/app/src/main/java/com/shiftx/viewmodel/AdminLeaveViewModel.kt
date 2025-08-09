package com.shiftx.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shiftx.data.db.entities.Employee
import com.shiftx.data.db.entities.Shift
import com.shiftx.data.repository.EmployeeRepository
import com.shiftx.data.repository.ShiftRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class AdminLeaveViewModel @Inject constructor(
    private val employeeRepository: EmployeeRepository,
    private val shiftRepository: ShiftRepository
) : ViewModel() {
    val employees = employeeRepository.getAllEmployees().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    val selectedEmployeeId = MutableStateFlow<String?>(null)
    val leaveType = MutableStateFlow("ANNUAL_LEAVE")
    val startDate = MutableStateFlow("")
    val endDate = MutableStateFlow("")

    fun updateEntitlements(employee: Employee, annual: Int, sick: Int) { viewModelScope.launch {
        employeeRepository.updateEmployee(employee.copy(annualLeaveEntitlement = annual, sickLeaveEntitlement = sick), passwordChanged = false)
    } }

    fun bookLeave() { viewModelScope.launch {
        val emp = selectedEmployeeId.value ?: return@launch
        val s = LocalDate.parse(startDate.value)
        val e = LocalDate.parse(endDate.value)
        var d = s
        val shifts = mutableListOf<Shift>()
        while (!d.isAfter(e)) {
            shifts.add(Shift(employeeId = emp, date = d.toString(), shiftType = leaveType.value))
            d = d.plusDays(1)
        }
        shiftRepository.upsertAll(shifts)
    } }
}