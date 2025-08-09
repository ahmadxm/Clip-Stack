package com.shiftx.ui.screens.employee

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shiftx.viewmodel.PreferencesViewModel

@Composable
fun EmployeePreferencesScreen(onBack: () -> Unit, viewModel: PreferencesViewModel = hiltViewModel()) {
    val theme by viewModel.theme.collectAsState()
    val timeFormat by viewModel.timeFormat.collectAsState()
    val shiftDisplay by viewModel.shiftDisplay.collectAsState()

    Scaffold(topBar = { TopAppBar(title = { Text("Preferences") }, navigationIcon = { TextButton(onClick = onBack) { Text("Back") } }) }) { padding ->
        Column(Modifier.padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text("Appearance")
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                FilterChip(selected = theme == "light", onClick = { viewModel.setTheme("light") }, label = { Text("Light") })
                FilterChip(selected = theme == "dark", onClick = { viewModel.setTheme("dark") }, label = { Text("Dark") })
                FilterChip(selected = theme == "system", onClick = { viewModel.setTheme("system") }, label = { Text("System") })
            }
            Divider()
            Text("Time Format")
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                FilterChip(selected = timeFormat == "12", onClick = { viewModel.setTimeFormat("12") }, label = { Text("12-hour") })
                FilterChip(selected = timeFormat == "24", onClick = { viewModel.setTimeFormat("24") }, label = { Text("24-hour") })
            }
            Divider()
            Text("Shift Display")
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                FilterChip(selected = shiftDisplay == "full", onClick = { viewModel.setShiftDisplay("full") }, label = { Text("Full") })
                FilterChip(selected = shiftDisplay == "abbr", onClick = { viewModel.setShiftDisplay("abbr") }, label = { Text("Abbreviated") })
            }
        }
    }
}