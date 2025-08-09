package com.shiftx.data.repository

import com.shiftx.data.db.dao.ShiftDao
import com.shiftx.data.db.entities.Shift
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShiftRepository @Inject constructor(
    private val shiftDao: ShiftDao
) {
    fun getShiftsForEmployeeBetween(employeeId: String, startDate: String, endDate: String): Flow<List<Shift>> =
        shiftDao.getShiftsForEmployeeBetween(employeeId, startDate, endDate)

    fun getShiftsBetween(startDate: String, endDate: String): Flow<List<Shift>> =
        shiftDao.getShiftsBetween(startDate, endDate)

    suspend fun upsert(shift: Shift) = shiftDao.upsert(shift)
    suspend fun upsertAll(shifts: List<Shift>) = shiftDao.upsertAll(shifts)
    suspend fun deleteRangeForEmployee(employeeId: String, startDate: String, endDate: String) =
        shiftDao.deleteRangeForEmployee(employeeId, startDate, endDate)
}