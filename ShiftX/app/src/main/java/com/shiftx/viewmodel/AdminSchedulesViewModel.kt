package com.shiftx.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shiftx.data.db.entities.Shift
import com.shiftx.data.repository.EmployeeRepository
import com.shiftx.data.repository.ShiftRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class AdminSchedulesViewModel @Inject constructor(
    private val employeeRepository: EmployeeRepository,
    private val shiftRepository: ShiftRepository
) : ViewModel() {
    val employees = employeeRepository.getAllEmployees().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    val selectedEmployeeId = MutableStateFlow<String?>(null)
    private val weekStart = MutableStateFlow(weekStart(LocalDate.now()))
    private val fmt = DateTimeFormatter.ISO_DATE

    val weeklyShifts: StateFlow<List<Shift>> = selectedEmployeeId.flatMapLatest { id ->
        val s = id ?: ""
        val start = weekStart.value
        val end = start.plusDays(6)
        shiftRepository.getShiftsForEmployeeBetween(s, start.format(fmt), end.format(fmt))
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun prevWeek() { weekStart.value = weekStart.value.minusWeeks(1) }
    fun nextWeek() { weekStart.value = weekStart.value.plusWeeks(1) }

    fun assignShift(date: String, shiftType: String) { viewModelScope.launch {
        val emp = selectedEmployeeId.value ?: return@launch
        shiftRepository.upsert(Shift(employeeId = emp, date = date, shiftType = shiftType))
    } }

    private fun weekStart(date: LocalDate): LocalDate = date.minusDays(((date.dayOfWeek.value + 6) % 7).toLong())
}