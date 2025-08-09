package com.shiftx.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employees")
data class Employee(
    @PrimaryKey val id: String,
    val name: String,
    val password: String,
    val jobTitle: String,
    val gender: String,
    val isActingInCharge: Boolean,
    val annualLeaveEntitlement: Int,
    val sickLeaveEntitlement: Int
)