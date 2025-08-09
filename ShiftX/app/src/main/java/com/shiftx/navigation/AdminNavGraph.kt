package com.shiftx.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.shiftx.ui.screens.admin.AdminDashboardScreen
import com.shiftx.ui.screens.admin.AddEditEmployeeScreen

fun NavGraphBuilder.adminNavGraph(navController: NavHostController) {
    navigation(startDestination = Routes.ADMIN_DASHBOARD, route = Routes.ADMIN_GRAPH) {
        composable(Routes.ADMIN_DASHBOARD) {
            AdminDashboardScreen(
                onAddEmployee = { navController.navigate(Routes.ADD_EMPLOYEE) },
                onEditEmployee = { id -> navController.navigate("edit_employee/$id") }
            )
        }
        composable(Routes.ADD_EMPLOYEE) {
            AddEditEmployeeScreen(onBack = { navController.popBackStack() })
        }
        composable("edit_employee/{employeeId}") {
            AddEditEmployeeScreen(onBack = { navController.popBackStack() })
        }
    }
}