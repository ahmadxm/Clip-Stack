package com.shiftx.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.shiftx.ui.screens.employee.EmployeeDashboardScreen
import com.shiftx.ui.screens.employee.EmployeePreferencesScreen
import com.shiftx.ui.screens.employee.ProfileScreen
import com.shiftx.viewmodel.SessionViewModel

fun NavGraphBuilder.employeeNavGraph(navController: NavHostController) {
    navigation(startDestination = Routes.EMPLOYEE_DASHBOARD, route = Routes.EMPLOYEE_GRAPH) {
        composable(Routes.EMPLOYEE_DASHBOARD) {
            EmployeeDashboardScreen(
                navigateToProfile = { navController.navigate(Routes.EMPLOYEE_PROFILE) },
                navigateToPreferences = { navController.navigate(Routes.EMPLOYEE_PREFERENCES) }
            )
        }
        composable(Routes.EMPLOYEE_PROFILE) {
            ProfileScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.EMPLOYEE_PREFERENCES) {
            EmployeePreferencesScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}