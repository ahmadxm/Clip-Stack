package com.shiftx.ui.screens.employee

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shiftx.viewmodel.EmployeeProfileViewModel

@Composable
fun ProfileScreen(onBack: () -> Unit, viewModel: EmployeeProfileViewModel = hiltViewModel()) {
    val employee = viewModel.employee.collectAsState().value
    val todayShift = viewModel.todayShift.collectAsState().value
    Scaffold(topBar = { TopAppBar(title = { Text("Profile") }, navigationIcon = { TextButton(onClick = onBack) { Text("Back") } }) }) { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {
            ElevatedCard(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("Name: ${employee?.name ?: "-"}")
                    Text("Job Title: ${employee?.jobTitle ?: "-"}")
                    Text("Gender: ${employee?.gender ?: "-"}")
                }
            }
            Spacer(Modifier.height(12.dp))
            ElevatedCard(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("Today's Shift: ${todayShift?.shiftType ?: "None"}")
                }
            }
            Spacer(Modifier.height(12.dp))
            ElevatedCard(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("Leave Balances")
                    LinearProgressIndicator(progress = ((employee?.annualLeaveEntitlement ?: 0) / 20f).coerceIn(0f, 1f))
                    LinearProgressIndicator(progress = ((employee?.sickLeaveEntitlement ?: 0) / 10f).coerceIn(0f, 1f))
                }
            }
            Spacer(Modifier.height(12.dp))
            ElevatedCard(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("Leave History [collapsible]")
                }
            }
            Spacer(Modifier.height(12.dp))
            ElevatedCard(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("Overtime Breakdown [collapsible]")
                }
            }
        }
    }
}