package com.shiftx.data.db.dao

import androidx.room.*
import com.shiftx.data.db.entities.Employee
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {
    @Query("SELECT * FROM employees ORDER BY name")
    fun getAllEmployeesFlow(): Flow<List<Employee>>

    @Query("SELECT * FROM employees WHERE id = :id LIMIT 1")
    fun getEmployeeByIdFlow(id: String): Flow<Employee?>

    @Query("SELECT * FROM employees WHERE id = :id LIMIT 1")
    suspend fun getEmployeeById(id: String): Employee?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(employee: Employee)

    @Update
    suspend fun update(employee: Employee)

    @Delete
    suspend fun delete(employee: Employee)

    @Query("SELECT COUNT(*) FROM employees WHERE id = :id")
    suspend fun countById(id: String): Int
}