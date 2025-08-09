package com.shiftx.data.db.dao

import androidx.room.*
import com.shiftx.data.db.entities.PublicHoliday
import kotlinx.coroutines.flow.Flow

@Dao
interface PublicHolidayDao {
    @Query("SELECT * FROM public_holidays ORDER BY date")
    fun getAllFlow(): Flow<List<PublicHoliday>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(holiday: PublicHoliday)

    @Delete
    suspend fun delete(holiday: PublicHoliday)
}