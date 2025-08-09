package com.shiftx.data.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "shifts",
    foreignKeys = [
        ForeignKey(
            entity = Employee::class,
            parentColumns = ["id"],
            childColumns = ["employeeId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["employeeId", "date"]) ]
)
data class Shift(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val employeeId: String,
    val date: String, // yyyy-MM-dd
    val shiftType: String
)