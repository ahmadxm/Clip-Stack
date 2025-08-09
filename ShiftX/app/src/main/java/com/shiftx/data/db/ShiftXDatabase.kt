package com.shiftx.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.shiftx.data.db.dao.EmployeeDao
import com.shiftx.data.db.dao.FillPayLaterDao
import com.shiftx.data.db.dao.PublicHolidayDao
import com.shiftx.data.db.dao.ShiftDao
import com.shiftx.data.db.entities.Employee
import com.shiftx.data.db.entities.FillPayLaterEntry
import com.shiftx.data.db.entities.PublicHoliday
import com.shiftx.data.db.entities.Shift
import com.shiftx.util.AESUtil
import org.json.JSONArray
import org.json.JSONObject

@Database(
    entities = [Employee::class, Shift::class, PublicHoliday::class, FillPayLaterEntry::class],
    version = 1,
    exportSchema = false
)
abstract class ShiftXDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
    abstract fun shiftDao(): ShiftDao
    abstract fun publicHolidayDao(): PublicHolidayDao
    abstract fun fplDao(): FillPayLaterDao

    companion object {
        @Volatile private var INSTANCE: ShiftXDatabase? = null

        fun getInstance(context: Context): ShiftXDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context): ShiftXDatabase {
            return Room.databaseBuilder(context, ShiftXDatabase::class.java, "shiftx.db")
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        try {
                            val json = context.assets.open("initial_data.json").bufferedReader().use { it.readText() }
                            val root = JSONObject(json)
                            val employees = root.optJSONArray("employees") ?: JSONArray()
                            val holidays = root.optJSONArray("publicHolidays") ?: JSONArray()

                            // Prepopulate employees
                            for (i in 0 until employees.length()) {
                                val e = employees.getJSONObject(i)
                                val id = e.getString("id")
                                val name = e.getString("name")
                                val passwordPlain = e.optString("password", "")
                                val passwordEnc = if (passwordPlain.isNotEmpty()) AESUtil.encrypt(passwordPlain) else ""
                                val jobTitle = e.getString("jobTitle")
                                val gender = e.getString("gender")
                                val isActing = e.optBoolean("isActingInCharge", false)
                                val annual = e.optInt("annualLeaveEntitlement", 0)
                                val sick = e.optInt("sickLeaveEntitlement", 0)
                                db.execSQL(
                                    "INSERT INTO employees(id, name, password, jobTitle, gender, isActingInCharge, annualLeaveEntitlement, sickLeaveEntitlement) VALUES(?, ?, ?, ?, ?, ?, ?, ?)",
                                    arrayOf(id, name, passwordEnc, jobTitle, gender, if (isActing) 1 else 0, annual, sick)
                                )
                            }
                            // Prepopulate public holidays
                            for (i in 0 until holidays.length()) {
                                val h = holidays.getJSONObject(i)
                                val date = h.getString("date")
                                val name = h.getString("name")
                                db.execSQL(
                                    "INSERT INTO public_holidays(date, name) VALUES(?, ?)",
                                    arrayOf(date, name)
                                )
                            }
                        } catch (_: Throwable) { /* ignore */ }
                    }
                })
                .build()
        }
    }
}