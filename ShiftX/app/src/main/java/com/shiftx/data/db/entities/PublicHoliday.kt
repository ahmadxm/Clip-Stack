package com.shiftx.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "public_holidays")
data class PublicHoliday(
    @PrimaryKey val date: String, // yyyy-MM-dd
    val name: String
)