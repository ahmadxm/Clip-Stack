package com.shiftx.ui.screens.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shiftx.data.db.entities.Employee
import com.shiftx.viewmodel.AdminDirectoryViewModel

@Composable
fun AdminDashboardScreen(
    onAddEmployee: () -> Unit,
    onEditEmployee: (String) -> Unit
) {
    var tab by remember { mutableStateOf(0) }
    val titles = listOf("Directory", "Schedules", "Leave", "Fill & Pay Later", "Holidays", "Export")

    Scaffold(topBar = { TopAppBar(title = { Text("Admin Dashboard") }) }, floatingActionButton = {
        if (tab == 0) FloatingActionButton(onClick = onAddEmployee) { Text("+") }
    }) { padding ->
        Column(Modifier.padding(padding)) {
            TabRow(selectedTabIndex = tab) {
                titles.forEachIndexed { i, t -> Tab(selected = tab == i, onClick = { tab = i }, text = { Text(t) }) }
            }
            when (tab) {
                0 -> DirectoryTab(onEditEmployee)
                1 -> SchedulesTab()
                2 -> LeaveTab()
                3 -> FplTab()
                4 -> HolidaysTab()
                5 -> ExportTab()
            }
        }
    }
}

@Composable private fun DirectoryTab(onEditEmployee: (String) -> Unit, vm: AdminDirectoryViewModel = hiltViewModel()) {
    val list by vm.filteredEmployees.collectAsState()
    var search by remember { mutableStateOf("") }
    LaunchedEffect(search) { vm.searchQuery.value = search }
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(value = search, onValueChange = { search = it }, label = { Text("Search") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        LazyColumn {
            items(list) { e -> EmployeeRow(e, onEdit = { onEditEmployee(e.id) }, onDelete = { vm.deleteEmployee(e) }) }
        }
    }
}

@Composable private fun EmployeeRow(e: Employee, onEdit: () -> Unit, onDelete: () -> Unit) {
    ElevatedCard(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(Modifier.padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(Modifier.weight(1f)) {
                Text(e.name, style = MaterialTheme.typography.titleMedium)
                Text("${e.jobTitle} • ${e.id}")
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TextButton(onClick = onEdit) { Text("Edit") }
                TextButton(onClick = onDelete, colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)) { Text("Delete") }
            }
        }
    }
}

@Composable private fun SchedulesTab() { Column(Modifier.fillMaxSize().padding(16.dp)) { Text("[Weekly planner to assign shifts]") } }
@Composable private fun LeaveTab() { Column(Modifier.fillMaxSize().padding(16.dp)) { Text("[Leave management UI]") } }
@Composable private fun FplTab() { Column(Modifier.fillMaxSize().padding(16.dp)) { Text("[Pending/Paid FPL lists]") } }
@Composable private fun HolidaysTab() { Column(Modifier.fillMaxSize().padding(16.dp)) { Text("[Add/Delete public holidays]") } }
@Composable private fun ExportTab() { Column(Modifier.fillMaxSize().padding(16.dp)) { Text("[Buttons to export PDFs]") } }