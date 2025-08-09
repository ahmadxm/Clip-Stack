package com.shiftx.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shiftx.data.db.entities.Employee
import com.shiftx.data.repository.EmployeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditEmployeeViewModel @Inject constructor(
    private val employeeRepository: EmployeeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val employeeIdArg: String? = savedStateHandle.get<String>("employeeId")

    val id = MutableStateFlow("")
    val name = MutableStateFlow("")
    val password = MutableStateFlow("")
    val jobTitle = MutableStateFlow("")
    val gender = MutableStateFlow("Male")
    val isActing = MutableStateFlow(false)
    val annualEnt = MutableStateFlow(14)
    val sickEnt = MutableStateFlow(7)

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    init {
        if (employeeIdArg != null) {
            viewModelScope.launch {
                employeeRepository.getEmployeeById(employeeIdArg)?.let { e ->
                    id.value = e.id
                    name.value = e.name
                    jobTitle.value = e.jobTitle
                    gender.value = e.gender
                    isActing.value = e.isActingInCharge
                    annualEnt.value = e.annualLeaveEntitlement
                    sickEnt.value = e.sickLeaveEntitlement
                    password.value = "" // require entering a new password to change
                }
            }
        }
    }

    fun save(onDone: () -> Unit) {
        viewModelScope.launch {
            _error.value = null
            val employee = Employee(
                id = id.value.trim(),
                name = name.value.trim(),
                password = password.value,
                jobTitle = jobTitle.value.trim(),
                gender = gender.value,
                isActingInCharge = isActing.value,
                annualLeaveEntitlement = annualEnt.value,
                sickLeaveEntitlement = sickEnt.value
            )
            if (employee.id.isEmpty() || employee.name.isEmpty()) {
                _error.value = "ID and Name are required"
                return@launch
            }
            try {
                if (employeeIdArg == null) {
                    // add
                    if (employeeRepository.getEmployeeById(employee.id) != null) {
                        _error.value = "Employee ID already exists"
                        return@launch
                    }
                    employeeRepository.addEmployee(employee)
                } else {
                    val passwordChanged = password.value.isNotEmpty()
                    employeeRepository.updateEmployee(employee, passwordChanged)
                }
                onDone()
            } catch (t: Throwable) {
                _error.value = t.message
            }
        }
    }
}