package com.shiftx.data.db.dao

import androidx.room.*
import com.shiftx.data.db.entities.FillPayLaterEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface FillPayLaterDao {
    @Query("SELECT * FROM fill_pay_later_entries WHERE status = :status ORDER BY date DESC")
    fun getByStatusFlow(status: String): Flow<List<FillPayLaterEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entry: FillPayLaterEntry)

    @Delete
    suspend fun delete(entry: FillPayLaterEntry)
}