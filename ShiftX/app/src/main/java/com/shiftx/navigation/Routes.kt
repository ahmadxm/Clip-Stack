package com.shiftx.navigation

object Routes {
    // Entry
    const val EMPLOYEE_LOGIN = "employee_login"
    const val ADMIN_LOGIN = "admin_login"

    // Employee graph
    const val EMPLOYEE_GRAPH = "employee_graph"
    const val EMPLOYEE_DASHBOARD = "employee_dashboard"
    const val EMPLOYEE_PROFILE = "employee_profile"
    const val EMPLOYEE_PREFERENCES = "employee_preferences"

    // Admin graph
    const val ADMIN_GRAPH = "admin_graph"
    const val ADMIN_DASHBOARD = "admin_dashboard"
    const val ADD_EMPLOYEE = "add_employee"
    const val EDIT_EMPLOYEE = "edit_employee/{employeeId}"
}