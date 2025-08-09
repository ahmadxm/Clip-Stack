package com.shiftx.data.db.dao

import androidx.room.*
import com.shiftx.data.db.entities.Shift
import kotlinx.coroutines.flow.Flow

@Dao
interface ShiftDao {
    @Query("SELECT * FROM shifts WHERE employeeId = :employeeId AND date BETWEEN :startDate AND :endDate ORDER BY date")
    fun getShiftsForEmployeeBetween(employeeId: String, startDate: String, endDate: String): Flow<List<Shift>>

    @Query("SELECT * FROM shifts WHERE date BETWEEN :startDate AND :endDate ORDER BY employeeId, date")
    fun getShiftsBetween(startDate: String, endDate: String): Flow<List<Shift>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(shift: Shift)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(shifts: List<Shift>)

    @Query("DELETE FROM shifts WHERE employeeId = :employeeId AND date BETWEEN :startDate AND :endDate")
    suspend fun deleteRangeForEmployee(employeeId: String, startDate: String, endDate: String)
}