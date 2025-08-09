package com.shiftx.data.repository

import com.shiftx.data.db.dao.FillPayLaterDao
import com.shiftx.data.db.entities.FillPayLaterEntry
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FplRepository @Inject constructor(
    private val dao: FillPayLaterDao
) {
    fun getPending(): Flow<List<FillPayLaterEntry>> = dao.getByStatusFlow("Pending")
    fun getPaid(): Flow<List<FillPayLaterEntry>> = dao.getByStatusFlow("Paid")
    suspend fun upsert(e: FillPayLaterEntry) = dao.upsert(e)
    suspend fun delete(e: FillPayLaterEntry) = dao.delete(e)
}