package com.shiftx.ui.screens.employee

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shiftx.viewmodel.EmployeeDashboardViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun EmployeeDashboardScreen(
    navigateToProfile: () -> Unit,
    navigateToPreferences: () -> Unit,
    viewModel: EmployeeDashboardViewModel = hiltViewModel()
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("My Schedule", "Full Roster")

    val weeklyShifts by viewModel.weeklyShifts.collectAsState()
    val monthlyShifts by viewModel.monthlyShifts.collectAsState()

    val fmt = remember { DateTimeFormatter.ISO_DATE }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ShiftX") },
                actions = {
                    TextButton(onClick = navigateToProfile) { Text("Profile") }
                    TextButton(onClick = navigateToPreferences) { Text("Preferences") }
                }
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding).fillMaxSize()) {
            ElevatedCard(Modifier.fillMaxWidth().padding(16.dp)) {
                Column(Modifier.padding(16.dp)) {
                    Text("Upcoming Public Holidays", style = MaterialTheme.typography.titleMedium)
                    Text("[Hook to holidays repo]")
                }
            }

            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(selected = selectedTab == index, onClick = { selectedTab = index }, text = { Text(title) })
                }
            }
            when (selectedTab) {
                0 -> MyScheduleTab(
                    onPrevWeek = { viewModel.prevWeek() },
                    onNextWeek = { viewModel.nextWeek() },
                    weeklyShifts = weeklyShifts,
                    onPrevMonth = { viewModel.prevMonth() },
                    onNextMonth = { viewModel.nextMonth() },
                    monthlyShifts = monthlyShifts
                )
                1 -> FullRosterTab()
            }
        }
    }
}

@Composable
private fun MyScheduleTab(
    onPrevWeek: () -> Unit,
    onNextWeek: () -> Unit,
    weeklyShifts: List<com.shiftx.data.db.entities.Shift>,
    onPrevMonth: () -> Unit,
    onNextMonth: () -> Unit,
    monthlyShifts: List<com.shiftx.data.db.entities.Shift>
) {
    var subTab by remember { mutableStateOf(0) }
    val titles = listOf("Weekly View", "Monthly View")
    Column(Modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = subTab) {
            titles.forEachIndexed { index, title ->
                Tab(selected = subTab == index, onClick = { subTab = index }, text = { Text(title) })
            }
        }
        when (subTab) {
            0 -> WeeklyView(onPrevWeek, onNextWeek, weeklyShifts)
            1 -> MonthlyView(onPrevMonth, onNextMonth, monthlyShifts)
        }
    }
}

@Composable
private fun WeeklyView(onPrev: () -> Unit, onNext: () -> Unit, shifts: List<com.shiftx.data.db.entities.Shift>) {
    Column(Modifier.fillMaxWidth().padding(16.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(onClick = onPrev) { Text("Prev") }
            OutlinedButton(onClick = onNext) { Text("Next") }
        }
        Spacer(Modifier.height(8.dp))
        LazyColumn {
            items(shifts) { shift ->
                ElevatedCard(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Column(Modifier.padding(12.dp)) {
                        Text(shift.date)
                        Text("Shift: ${shift.shiftType}")
                    }
                }
            }
        }
    }
}

@Composable
private fun MonthlyView(onPrev: () -> Unit, onNext: () -> Unit, shifts: List<com.shiftx.data.db.entities.Shift>) {
    Column(Modifier.fillMaxWidth().padding(16.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(onClick = onPrev) { Text("Prev") }
            OutlinedButton(onClick = onNext) { Text("Next") }
        }
        Spacer(Modifier.height(8.dp))
        LazyColumn {
            items(shifts) { shift ->
                Text("${shift.date}: ${shift.shiftType}")
            }
        }
    }
}

@Composable
private fun FullRosterTab() {
    Column(Modifier.fillMaxWidth().padding(16.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(onClick = { /* prev week */ }) { Text("Prev") }
            OutlinedButton(onClick = { /* next week */ }) { Text("Next") }
        }
        Spacer(Modifier.height(8.dp))
        Text("[Full roster list grouped by job title]")
    }
}