package com.shiftx.data.repository

import com.shiftx.data.db.dao.EmployeeDao
import com.shiftx.data.db.entities.Employee
import com.shiftx.util.AESUtil
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmployeeRepository @Inject constructor(
    private val employeeDao: EmployeeDao
) {
    fun getAllEmployees(): Flow<List<Employee>> = employeeDao.getAllEmployeesFlow()
    fun getEmployeeByIdFlow(id: String): Flow<Employee?> = employeeDao.getEmployeeByIdFlow(id)
    suspend fun getEmployeeById(id: String): Employee? = employeeDao.getEmployeeById(id)

    suspend fun addEmployee(employee: Employee) {
        val secured = employee.copy(password = securePassword(employee.password))
        employeeDao.insert(secured)
    }

    suspend fun updateEmployee(employee: Employee, passwordChanged: Boolean) {
        val updated = if (passwordChanged) employee.copy(password = securePassword(employee.password)) else employee
        employeeDao.update(updated)
    }

    suspend fun deleteEmployee(employee: Employee) = employeeDao.delete(employee)

    suspend fun validateCredentials(id: String, password: String): Boolean {
        val e = employeeDao.getEmployeeById(id) ?: return false
        return try {
            AESUtil.decrypt(e.password) == password
        } catch (_: Throwable) {
            false
        }
    }

    private fun securePassword(raw: String): String = AESUtil.encrypt(raw)
}