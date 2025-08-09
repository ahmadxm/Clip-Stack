package com.shiftx.data.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "fill_pay_later_entries",
    foreignKeys = [
        ForeignKey(
            entity = Employee::class,
            parentColumns = ["id"],
            childColumns = ["employeeId"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class FillPayLaterEntry(
    @PrimaryKey val id: String, // UUID
    val customerName: String,
    val amount: Double,
    val date: String, // ISO 8601
    val status: String, // Pending or Paid
    val notes: String?,
    val employeeId: String?
)