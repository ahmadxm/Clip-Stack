package com.shiftx.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shiftx.data.db.entities.PublicHoliday
import com.shiftx.data.repository.PublicHolidayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HolidaysViewModel @Inject constructor(
    private val repo: PublicHolidayRepository
) : ViewModel() {
    val holidays: StateFlow<List<PublicHoliday>> = repo.getAll().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun add(date: String, name: String) { viewModelScope.launch { repo.upsert(PublicHoliday(date, name)) } }
    fun delete(h: PublicHoliday) { viewModelScope.launch { repo.delete(h) } }
}