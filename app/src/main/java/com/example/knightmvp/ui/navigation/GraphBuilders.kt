package com.example.knightmvp.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation

interface GraphBuilder {
    fun buildGraph(
        navGraphBuilder: NavGraphBuilder,
        navigationActions: NavigationActions,
        onMenuClick: () -> Unit,
        onExitApp: () -> Unit
    )
}

abstract class BaseGraphBuilder(
    private val screenFactory: ScreenFactory
) : GraphBuilder {
    
    protected abstract val graphRoute: String
    protected abstract val startDestination: String
    protected abstract val routes: List<String>
    
    override fun buildGraph(
        navGraphBuilder: NavGraphBuilder,
        navigationActions: NavigationActions,
        onMenuClick: () -> Unit,
        onExitApp: () -> Unit
    ) {
        navGraphBuilder.navigation(
            startDestination = startDestination,
            route = graphRoute
        ) {
            routes.forEach { route ->
                composable(route) {
                    // Добавляем BackHandler для начальных экранов графов
                    if (route == startDestination) {
                        BackHandler {
                            onExitApp()
                        }
                    }
                    
                    screenFactory.createScreen(route, navigationActions, onMenuClick)()
                }
            }
        }
    }
}

class HomeGraphBuilder(screenFactory: ScreenFactory) : BaseGraphBuilder(screenFactory) {
    public override val graphRoute: String = Graph.Home.route
    public override val startDestination: String = Screen.Home.route
    public override val routes: List<String> = listOf(
        Screen.Home.route,
        Screen.About.route,
        Screen.Settings.route
    )
}

class SecondGraphBuilder(screenFactory: ScreenFactory) : BaseGraphBuilder(screenFactory) {
    public override val graphRoute: String = Graph.Second.route
    public override val startDestination: String = Screen.Second.route
    public override val routes: List<String> = listOf(
        Screen.Second.route,
        Screen.Profile.route
    )
}

class DetailGraphBuilder(screenFactory: ScreenFactory) : BaseGraphBuilder(screenFactory) {
    public override val graphRoute: String = Graph.Detail.route
    public override val startDestination: String = Screen.Detail.route
    public override val routes: List<String> = listOf(
        Screen.Detail.route,
        Screen.Info.route
    )
}
