package com.shiftx.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shiftx.data.db.entities.Shift
import com.shiftx.data.repository.ShiftRepository
import com.shiftx.datastore.SessionStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class EmployeeDashboardViewModel @Inject constructor(
    private val shiftRepository: ShiftRepository,
    private val sessionStore: SessionStore
) : ViewModel() {
    private val formatter = DateTimeFormatter.ISO_DATE

    private val currentWeekStart = MutableStateFlow(weekStart(LocalDate.now()))
    private val currentMonth = MutableStateFlow(LocalDate.now().withDayOfMonth(1))

    val weekLabel: StateFlow<String> = currentWeekStart.stateIn(viewModelScope, SharingStarted.Eagerly, LocalDate.now().toString())
    val monthLabel: StateFlow<String> = currentMonth.stateIn(viewModelScope, SharingStarted.Eagerly, LocalDate.now().toString())

    val weeklyShifts: StateFlow<List<Shift>> = sessionStore.loggedInEmployeeIdFlow.flatMapLatest { id ->
        val start = currentWeekStart.value
        val end = start.plusDays(6)
        val s = id ?: ""
        shiftRepository.getShiftsForEmployeeBetween(s, start.format(formatter), end.format(formatter))
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val monthlyShifts: StateFlow<List<Shift>> = sessionStore.loggedInEmployeeIdFlow.flatMapLatest { id ->
        val first = currentMonth.value
        val last = first.withDayOfMonth(first.lengthOfMonth())
        val s = id ?: ""
        shiftRepository.getShiftsForEmployeeBetween(s, first.format(formatter), last.format(formatter))
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun prevWeek() { currentWeekStart.value = currentWeekStart.value.minusWeeks(1) }
    fun nextWeek() { currentWeekStart.value = currentWeekStart.value.plusWeeks(1) }

    fun prevMonth() { currentMonth.value = currentMonth.value.minusMonths(1) }
    fun nextMonth() { currentMonth.value = currentMonth.value.plusMonths(1) }

    private fun weekStart(date: LocalDate): LocalDate = date.minusDays(((date.dayOfWeek.value + 6) % 7).toLong())
}