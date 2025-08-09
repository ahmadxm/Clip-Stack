package com.shiftx.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shiftx.ui.screens.admin.AdminLoginScreen
import com.shiftx.ui.screens.employee.EmployeeLoginScreen
import com.shiftx.viewmodel.SessionViewModel

@Composable
fun ShiftXNavGraph(navController: NavHostController = rememberNavController()) {
    val sessionViewModel: SessionViewModel = hiltViewModel()
    val startDestination = when {
        sessionViewModel.isAdminLoggedInState.value -> Routes.ADMIN_GRAPH
        sessionViewModel.loggedInEmployeeIdState.value != null -> Routes.EMPLOYEE_GRAPH
        else -> Routes.EMPLOYEE_LOGIN
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Routes.EMPLOYEE_LOGIN) {
            EmployeeLoginScreen(
                onAdminClick = { navController.navigate(Routes.ADMIN_LOGIN) },
                onSuccess = {
                    navController.navigate(Routes.EMPLOYEE_GRAPH) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.ADMIN_LOGIN) {
            AdminLoginScreen(
                onBack = { navController.popBackStack() },
                onSuccess = {
                    navController.navigate(Routes.ADMIN_GRAPH) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        employeeNavGraph(navController)
        adminNavGraph(navController)
    }
}