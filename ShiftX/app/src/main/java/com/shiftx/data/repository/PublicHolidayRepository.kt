package com.shiftx.data.repository

import com.shiftx.data.db.dao.PublicHolidayDao
import com.shiftx.data.db.entities.PublicHoliday
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PublicHolidayRepository @Inject constructor(
    private val dao: PublicHolidayDao
) {
    fun getAll(): Flow<List<PublicHoliday>> = dao.getAllFlow()
    suspend fun upsert(h: PublicHoliday) = dao.upsert(h)
    suspend fun delete(h: PublicHoliday) = dao.delete(h)
}