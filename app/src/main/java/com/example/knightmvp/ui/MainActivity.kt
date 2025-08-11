package com.example.knightmvp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.knightmvp.ui.navigation.AppNavigation
import com.example.knightmvp.ui.navigation.Graph
import com.example.knightmvp.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.knightmvp.R
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.ui.res.colorResource

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainScreen(
                    onExitApp = { finish() }
                )
            }
        }
    }
}

@Composable
private fun MainScreen(
    onExitApp: () -> Unit
) {
    val navigationState = rememberNavigationState()

    ModalNavigationDrawer(
        drawerState = navigationState.drawerState,
        drawerContent = {
            Box(
                modifier = Modifier.width(200.dp)
            ) {
                NavigationDrawerContent(
                    navigationState = navigationState
                )
            }
        }
    ) {
        MainContent(
            navigationState = navigationState,
            onExitApp = onExitApp
        )
    }
}

/**
 * Состояние навигации
 */
private data class NavigationState(
    val drawerState: DrawerState,
    val navController: NavHostController,
    val scope: CoroutineScope
) {
    fun navigateToGraph(route: String) {
        navController.navigate(route) {
            popUpTo(0) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }
}

/**
 * Состояние навигации + обьединяем ее логику в этой функции
 */
@Composable
private fun rememberNavigationState(): NavigationState {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    return NavigationState(
        drawerState = drawerState,
        navController = navController,
        scope = scope
    )
}

@Composable
private fun MainContent(
    navigationState: NavigationState,
    onExitApp: () -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        AppNavigation(
            navController = navigationState.navController,
            modifier = Modifier.padding(innerPadding),
            onMenuClick = {
                navigationState.scope.launch {
                    navigationState.drawerState.open()
                }
            },
            onExitApp = onExitApp
        )
    }
}

/**
 *  Навигационный drawer
 */
@Composable
private fun NavigationDrawerContent(
    navigationState: NavigationState
) {
    val navBackStackEntry = navigationState.navController.currentBackStackEntryAsState().value
    val currentDestination = navBackStackEntry?.destination
    val isDrawerOpen = navigationState.drawerState.currentValue == DrawerValue.Open

    ModalDrawerSheet {
        DrawerHeader(isOpen = isDrawerOpen)
        DrawerDivider(isOpen = isDrawerOpen)
        DrawerMenuItems(
            navigationState = navigationState,
            currentDestination = currentDestination,
            isOpen = isDrawerOpen
        )
    }
}

/**
 * Заголовок drawer
 */
@Composable
private fun DrawerHeader(isOpen: Boolean) {
    AnimatedVisibility(
        visible = isOpen,
        enter = fadeIn(animationSpec = tween(durationMillis = 300)),
        exit = fadeOut(animationSpec = tween(durationMillis = 150))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.sun_moon_cloud),
                contentDescription = "Солнце, Луна и облако",
                modifier = Modifier
                    .padding(top = 16.dp)
                    .size(180.dp)
                    .clip(RoundedCornerShape(20.dp))
            )

        }
    }
}

/**
 * Разделитель в drawer
 */
@Composable
private fun DrawerDivider(isOpen: Boolean) {
    AnimatedVisibility(
        visible = isOpen,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = 400,
                delayMillis = 100
            )
        ),
        exit = fadeOut(animationSpec = tween(durationMillis = 100))
    ) {
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

/**
 * Меню drawer
 */
@Composable
private fun DrawerMenuItems(
    navigationState: NavigationState,
    currentDestination: NavDestination?,
    isOpen: Boolean
) {
    val menuItems = createMenuItems(currentDestination)

    menuItems.forEachIndexed { index, item ->
        AnimatedMenuItem(
            visible = isOpen,
            delayMs = index * 60,
            content = {
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = null
                        )
                    },
                    label = { Text(item.label) },
                    selected = item.isSelected,
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = colorResource(id = R.color.purple_700).copy(alpha = 0f),
                        selectedIconColor = colorResource(id = R.color.purple_700),
                        selectedTextColor = colorResource(id = R.color.purple_700),
                        unselectedContainerColor = Color.Transparent
                    ),
                    onClick = {
                        navigationState.navigateToGraph(item.route)
                        navigationState.scope.launch {
                            navigationState.drawerState.close()
                        }
                    }
                )
            }
        )
    }
}

/**
 * Анимированный пункт меню
 */
@Composable
private fun AnimatedMenuItem(
    visible: Boolean,
    delayMs: Int,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = 220,
                delayMillis = delayMs
            )
        ) + slideInHorizontally(
            initialOffsetX = { fullWidth -> -fullWidth / 4 },
            animationSpec = tween(
                durationMillis = 220,
                delayMillis = delayMs
            )
        ),
        exit = fadeOut(animationSpec = tween(durationMillis = 120))
    ) {
        content()
    }
}

/**
 * Создаем список пунктов меню
 */
private fun createMenuItems(currentDestination: NavDestination?): List<MenuItem> {
    val isInHomeGraph = currentDestination?.hierarchy?.any { it.route == Graph.Home.route } == true
    val isInSecondGraph =
        currentDestination?.hierarchy?.any { it.route == Graph.Second.route } == true
    val isInDetailGraph =
        currentDestination?.hierarchy?.any { it.route == Graph.Detail.route } == true
    return listOf(
        MenuItem(
            icon = Icons.Outlined.Home,
            label = "Главная",
            route = Graph.Home.route,
            isSelected = isInHomeGraph
        ),
        MenuItem(
            icon = Icons.Outlined.Star,
            label = "Второй экран",
            route = Graph.Second.route,
            isSelected = isInSecondGraph
        ),
        MenuItem(
            icon = Icons.Outlined.Info,
            label = "Детали",
            route = Graph.Detail.route,
            isSelected = isInDetailGraph
        )
    )
}

/**
 * Пункт меню
 */
private data class MenuItem(
    val icon: ImageVector,
    val label: String,
    val route: String,
    val isSelected: Boolean
)

@Preview
@Composable
private fun MainActivityPreview() {
    MyApplicationTheme {
        MainScreen(
            onExitApp = { }
        )
    }
}

