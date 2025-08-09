package com.shiftx.ui.screens.employee

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shiftx.viewmodel.EmployeeLoginViewModel

@Composable
fun EmployeeLoginScreen(
    onAdminClick: () -> Unit,
    onSuccess: () -> Unit,
    viewModel: EmployeeLoginViewModel = hiltViewModel()
) {
    var employeeId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "ShiftX", style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(24.dp))
        OutlinedTextField(value = employeeId, onValueChange = { employeeId = it }, label = { Text("Employee ID") }, singleLine = true)
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, visualTransformation = PasswordVisualTransformation(), singleLine = true)
        Spacer(Modifier.height(12.dp))
        Button(onClick = { viewModel.login(employeeId, password, onSuccess) }, enabled = !loading) {
            Text("Sign In")
        }
        TextButton(onClick = onAdminClick) { Text("Admin Login") }
        if (error != null) {
            Spacer(Modifier.height(8.dp))
            Text(text = error ?: "", color = MaterialTheme.colorScheme.error)
        }
    }
}