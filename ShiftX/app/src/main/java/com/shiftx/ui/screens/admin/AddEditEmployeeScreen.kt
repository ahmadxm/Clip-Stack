package com.shiftx.ui.screens.admin

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shiftx.viewmodel.AddEditEmployeeViewModel

@Composable
fun AddEditEmployeeScreen(onBack: () -> Unit, viewModel: AddEditEmployeeViewModel = hiltViewModel()) {
    val id by viewModel.id.collectAsState()
    val name by viewModel.name.collectAsState()
    val password by viewModel.password.collectAsState()
    val jobTitle by viewModel.jobTitle.collectAsState()
    val gender by viewModel.gender.collectAsState()
    val isActing by viewModel.isActing.collectAsState()
    val annualEnt by viewModel.annualEnt.collectAsState()
    val sickEnt by viewModel.sickEnt.collectAsState()
    val error by viewModel.error.collectAsState(null)

    Scaffold(topBar = { TopAppBar(title = { Text("Add/Edit Employee") }, navigationIcon = { TextButton(onClick = onBack) { Text("Back") } }) }) { padding ->
        Column(Modifier.padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedTextField(id, { viewModel.id.value = it }, label = { Text("Employee ID") }, singleLine = true)
            OutlinedTextField(name, { viewModel.name.value = it }, label = { Text("Name") }, singleLine = true)
            OutlinedTextField(password, { viewModel.password.value = it }, label = { Text("Password (leave blank to keep)") }, singleLine = true)
            OutlinedTextField(jobTitle, { viewModel.jobTitle.value = it }, label = { Text("Job Title") }, singleLine = true)
            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                Text("Gender: ")
                Spacer(Modifier.width(8.dp))
                FilterChip(selected = gender == "Male", onClick = { viewModel.gender.value = "Male" }, label = { Text("Male") })
                Spacer(Modifier.width(8.dp))
                FilterChip(selected = gender == "Female", onClick = { viewModel.gender.value = "Female" }, label = { Text("Female") })
            }
            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Acting In Charge")
                Switch(checked = isActing, onCheckedChange = { viewModel.isActing.value = it })
            }
            OutlinedTextField(annualEnt.toString(), { it.toIntOrNull()?.let { v -> viewModel.annualEnt.value = v } }, label = { Text("Annual Leave Entitlement") }, singleLine = true)
            OutlinedTextField(sickEnt.toString(), { it.toIntOrNull()?.let { v -> viewModel.sickEnt.value = v } }, label = { Text("Sick Leave Entitlement") }, singleLine = true)
            if (error != null) Text(text = error ?: "", color = MaterialTheme.colorScheme.error)
            Button(onClick = { viewModel.save(onBack) }, modifier = Modifier.fillMaxWidth()) { Text("Save") }
        }
    }
}