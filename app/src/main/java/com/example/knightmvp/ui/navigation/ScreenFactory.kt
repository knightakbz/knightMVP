package com.example.knightmvp.ui.navigation

import androidx.compose.runtime.Composable
import com.example.knightmvp.ui.compose.DetailScreen
import com.example.knightmvp.ui.compose.HomeScreen
import com.example.knightmvp.ui.compose.SecondScreen
import com.example.knightmvp.ui.compose.AboutScreen
import com.example.knightmvp.ui.compose.SettingsScreen
import com.example.knightmvp.ui.compose.ProfileScreen
import com.example.knightmvp.ui.compose.InfoScreen

/**
 * Интерфейс для создания экранов
 */
interface ScreenFactory {
    fun createScreen(
        route: String,
        navigationActions: NavigationActions,
        onMenuClick: () -> Unit
    ): @Composable () -> Unit
}

/**
 * Реализация для создания экранов
 */
class ScreenFactoryImpl : ScreenFactory {
    
    override fun createScreen(
        route: String,
        navigationActions: NavigationActions,
        onMenuClick: () -> Unit
    ): @Composable () -> Unit = {
        when (route) {
            Screen.Home.route -> HomeScreen(
                onAboutClick = { navigationActions.navigateToScreen(Screen.About.route) },
                onSettingsClick = { navigationActions.navigateToScreen(Screen.Settings.route) },
                onMenuClick = onMenuClick
            )
            Screen.About.route -> AboutScreen(
                onMenuClick = onMenuClick,
                onNavigateBack = { 
                    if (!navigationActions.popBackStack()) {
                        navigationActions.exitApp()
                    }
                }
            )
            Screen.Settings.route -> SettingsScreen(
                onMenuClick = onMenuClick,
                onNavigateBack = { 
                    if (!navigationActions.popBackStack()) {
                        navigationActions.exitApp()
                    }
                }
            )
            Screen.Second.route -> SecondScreen(
                onNavigateToDetail = { navigationActions.navigateToGraph(Graph.Detail.route) },
                onProfileClick = { navigationActions.navigateToScreen(Screen.Profile.route) },
                onMenuClick = onMenuClick
            )
            Screen.Profile.route -> ProfileScreen(
                onMenuClick = onMenuClick,
                onNavigateBack = { 
                    if (!navigationActions.popBackStack()) {
                        navigationActions.exitApp()
                    }
                }
            )
            Screen.Detail.route -> DetailScreen(
                onNavigateBack = { 
                    if (!navigationActions.popBackStack()) {
                        navigationActions.exitApp()
                    }
                },
                onInfoClick = { navigationActions.navigateToScreen(Screen.Info.route) },
                onMenuClick = onMenuClick
            )
            Screen.Info.route -> InfoScreen(
                onMenuClick = onMenuClick,
                onNavigateBack = { 
                    if (!navigationActions.popBackStack()) {
                        navigationActions.exitApp()
                    }
                }
            )
            else -> throw IllegalArgumentException("Unknown route: $route")
        }
    }
}
