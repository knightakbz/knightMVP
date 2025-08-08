package com.example.knightmvp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

/**
 * Экраны приложения
 */
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object About : Screen("about")
    object Settings : Screen("settings")
    object Second : Screen("second")
    object Profile : Screen("profile")
    object Detail : Screen("detail")
    object Info : Screen("info")
}

/**
 * Ветки графа приложения
 */
sealed class Graph(val route: String) {
    object Home : Graph("home_graph")
    object Second : Graph("second_graph")
    object Detail : Graph("detail_graph")
}

/**
 * @param navController Контроллер навигации
 * @param modifier Модификатор для настройки внешнего вида
 * @param onMenuClick Callback для открытия бокового меню
 * @param onExitApp Callback для выхода из приложения
 */
@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit = {},
    onExitApp: () -> Unit = {}
) {
    val navigationActions = NavigationActionsImpl(navController, onExitApp)
    val screenFactory = ScreenFactoryImpl()

    val graphBuilders = listOf(
        HomeGraphBuilder(screenFactory),
        SecondGraphBuilder(screenFactory),
        DetailGraphBuilder(screenFactory)
    )

    NavHost(
        navController = navController,
        startDestination = Graph.Home.route,
        modifier = modifier
    ) {
        graphBuilders.forEach { builder ->
            builder.buildGraph(this, navigationActions, onMenuClick, onExitApp)
        }
    }
}
