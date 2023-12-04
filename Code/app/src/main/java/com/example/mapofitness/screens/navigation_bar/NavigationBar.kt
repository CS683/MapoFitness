package com.example.mapofitness.screens.navigation_bar

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

enum class BottomTab(val route: String, val icon: ImageVector, val label: String) {
    Home("home", Icons.Filled.Home, "Home"),
    Profile("profile", Icons.Filled.Person, "Profile")
}
@Composable
fun NavigationBar(navController: NavController){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = TopEdgeShape
            )
    ){
        BottomNavigation (
            backgroundColor = MaterialTheme.colorScheme.background,
        ){
            val currentRoute = currentRoute(navController)
            BottomTab.values().forEach { tab ->
                BottomNavigationItem(
                    icon = {
                        val iconColor = if (currentRoute == tab.route) {
                            MaterialTheme.colorScheme.primary
                        }else{
                            MaterialTheme.colorScheme.onSurface
                        }
                        Icon(tab.icon,  contentDescription = null, tint = iconColor)
                    },
                    selected = currentRoute == tab.route,
                    onClick = {
                        if (currentRoute != tab.route) {
                            navController.navigate(tab.route) {
                                restoreState = true
                                launchSingleTop = true
                                popUpTo(navController.graph.startDestinationRoute!!) {
                                    saveState = true
                                }
                            }
                        }
                    },
                )
            }
        }
    }
}

@Composable
private fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

object TopEdgeShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(size.width, 0.2f)
        }
        return Outline.Generic(path)
    }
}