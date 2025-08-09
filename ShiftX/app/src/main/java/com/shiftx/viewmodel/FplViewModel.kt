package com.shiftx.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shiftx.data.db.entities.FillPayLaterEntry
import com.shiftx.data.repository.FplRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class FplViewModel @Inject constructor(
    private val repo: FplRepository
) : ViewModel() {
    val pending: StateFlow<List<FillPayLaterEntry>> = repo.getPending().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    val paid: StateFlow<List<FillPayLaterEntry>> = repo.getPaid().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun add(customerName: String, amount: Double, date: String, notes: String?, employeeId: String?) {
        viewModelScope.launch { repo.upsert(FillPayLaterEntry(UUID.randomUUID().toString(), customerName, amount, date, "Pending", notes, employeeId)) }
    }

    fun update(entry: FillPayLaterEntry) { viewModelScope.launch { repo.upsert(entry) } }
    fun delete(entry: FillPayLaterEntry) { viewModelScope.launch { repo.delete(entry) } }
    fun markPaid(entry: FillPayLaterEntry) { viewModelScope.launch { repo.upsert(entry.copy(status = "Paid")) } }
}