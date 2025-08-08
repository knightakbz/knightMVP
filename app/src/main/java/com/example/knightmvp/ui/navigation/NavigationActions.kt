package com.example.knightmvp.ui.navigation

import androidx.navigation.NavHostController

/**
 * Интерфейс для навигационных действий
 */
interface NavigationActions {
    fun navigateToScreen(route: String)
    fun navigateToGraph(graphRoute: String)
    fun popBackStack(): Boolean
    fun exitApp()
}

/**
 * Реализация навигационных действий
 */
class NavigationActionsImpl(
    private val navController: NavHostController,
    private val onExitApp: () -> Unit
) : NavigationActions {
    
    override fun navigateToScreen(route: String) {
        navController.navigate(route)
    }
    
    override fun navigateToGraph(graphRoute: String) {
        navController.navigate(graphRoute)
    }
    
    override fun popBackStack(): Boolean {
        return navController.popBackStack()
    }
    
    override fun exitApp() {
        onExitApp()
    }
}
