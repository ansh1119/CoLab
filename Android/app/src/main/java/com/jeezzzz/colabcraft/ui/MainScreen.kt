package com.jeezzzz.colabcraft.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jeezzzz.colabcraft.data.repository.CollabRepository
import com.jeezzzz.colabcraft.ui.auth.LoginScreen
import com.jeezzzz.colabcraft.ui.auth.RegisterScreen
import com.jeezzzz.colabcraft.ui.chat.ChatScreen
import com.jeezzzz.colabcraft.ui.chat.InboxScreen
import com.jeezzzz.colabcraft.ui.detail.HackathonDetailScreen
import com.jeezzzz.colabcraft.ui.home.HomeScreen
import com.jeezzzz.colabcraft.ui.notification.NotificationScreen
import com.jeezzzz.colabcraft.ui.post.CreatePostScreen
import com.jeezzzz.colabcraft.ui.profile.ProfileScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    repository: CollabRepository
) : ViewModel() {
    val isLoggedIn: StateFlow<Boolean?> = repository.userFlow
        .map { it != null }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
}

sealed class Screen(val route: String, val icon: ImageVector, val title: String) {
    object Home : Screen("home", Icons.Default.Home, "Home")
    object Create : Screen("create_post", Icons.Default.Add, "Create")
    object Inbox : Screen("inbox", Icons.Default.Email, "Inbox")
    object Profile : Screen("profile", Icons.Default.Person, "Profile")
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val sessionViewModel: SessionViewModel = hiltViewModel()
    val isLoggedIn by sessionViewModel.isLoggedIn.collectAsState()

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn == true) {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    val bottomNavRoutes = setOf("home", "create_post", "profile", "inbox")
    val showBottomBar = currentDestination?.route in bottomNavRoutes

    val items = listOf(Screen.Home, Screen.Inbox, Screen.Create, Screen.Profile)

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                Surface(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                    shape = RoundedCornerShape(28.dp),
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                    tonalElevation = 8.dp,
                    shadowElevation = 16.dp,
                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.08f))
                ) {
                    NavigationBar(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.onSurface,
                        tonalElevation = 0.dp
                    ) {
                        items.forEach { screen ->
                            val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
                            NavigationBarItem(
                                icon = {
                                    Icon(
                                        screen.icon,
                                        contentDescription = null,
                                        modifier = Modifier.size(if (selected) 26.dp else 22.dp)
                                    )
                                },
                                label = {
                                    Text(
                                        screen.title,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                selected = selected,
                                onClick = {
                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.padding(innerPadding),
            enterTransition = { fadeIn(animationSpec = tween(250)) },
            exitTransition = { fadeOut(animationSpec = tween(250)) },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -300 }) + fadeIn(tween(250)) },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { 300 }) + fadeOut(tween(250)) }
        ) {
            composable("login") { LoginScreen(navController) }
            composable("register") { RegisterScreen(navController) }

            composable(
                "home",
                enterTransition = { fadeIn(animationSpec = tween(300)) },
                exitTransition = { fadeOut(animationSpec = tween(200)) }
            ) { HomeScreen(navController) }

            composable(
                "create_post",
                enterTransition = { fadeIn(animationSpec = tween(300)) },
                exitTransition = { fadeOut(animationSpec = tween(200)) }
            ) { CreatePostScreen(navController) }

            composable(
                "inbox",
                enterTransition = { fadeIn(animationSpec = tween(300)) },
                exitTransition = { fadeOut(animationSpec = tween(200)) }
            ) { InboxScreen(navController) }

            composable(
                "profile",
                enterTransition = { fadeIn(animationSpec = tween(300)) },
                exitTransition = { fadeOut(animationSpec = tween(200)) }
            ) { ProfileScreen(navController) }

            composable("hackathon_detail/{hackathonId}") { HackathonDetailScreen(navController) }

            composable(
                "chat/{teamId}",
                enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn(tween(300)) },
                exitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) + fadeOut(tween(300)) },
                popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }) + fadeIn(tween(300)) },
                popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) + fadeOut(tween(300)) }
            ) { ChatScreen(navController) }

            composable(
                "notifications",
                enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn(tween(300)) },
                popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) + fadeOut(tween(300)) }
            ) { NotificationScreen(navController) }
        }
    }
}
