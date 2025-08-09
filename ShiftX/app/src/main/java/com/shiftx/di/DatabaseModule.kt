package com.shiftx.di

import android.content.Context
import com.shiftx.data.db.ShiftXDatabase
import com.shiftx.data.db.dao.EmployeeDao
import com.shiftx.data.db.dao.FillPayLaterDao
import com.shiftx.data.db.dao.PublicHolidayDao
import com.shiftx.data.db.dao.ShiftDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ShiftXDatabase = ShiftXDatabase.getInstance(context)

    @Provides fun provideEmployeeDao(db: ShiftXDatabase): EmployeeDao = db.employeeDao()
    @Provides fun provideShiftDao(db: ShiftXDatabase): ShiftDao = db.shiftDao()
    @Provides fun provideHolidayDao(db: ShiftXDatabase): PublicHolidayDao = db.publicHolidayDao()
    @Provides fun provideFplDao(db: ShiftXDatabase): FillPayLaterDao = db.fplDao()
}