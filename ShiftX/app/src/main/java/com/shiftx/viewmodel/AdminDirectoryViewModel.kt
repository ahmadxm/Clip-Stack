package com.shiftx.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shiftx.data.db.entities.Employee
import com.shiftx.data.repository.EmployeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminDirectoryViewModel @Inject constructor(
    private val employeeRepository: EmployeeRepository
) : ViewModel() {
    val searchQuery = MutableStateFlow("")
    val selectedJobTitle = MutableStateFlow("All")

    private val allEmployeesFlow = employeeRepository.getAllEmployees()

    val jobTitles: StateFlow<List<String>> = allEmployeesFlow.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList()).let { sf ->
        MutableStateFlow(emptyList<String>())
    }

    val filteredEmployees: StateFlow<List<Employee>> =
        combine(allEmployeesFlow, searchQuery, selectedJobTitle) { employees, query, title ->
            val q = query.trim().lowercase()
            employees.filter { e ->
                (q.isEmpty() || e.name.lowercase().contains(q) || e.id.lowercase().contains(q)) &&
                (title == "All" || e.jobTitle == title)
            }
        }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun deleteEmployee(e: Employee) { viewModelScope.launch { employeeRepository.deleteEmployee(e) } }
}