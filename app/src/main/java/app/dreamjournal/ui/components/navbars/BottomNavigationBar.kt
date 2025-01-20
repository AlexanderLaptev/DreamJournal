package app.dreamjournal.ui.components.navbars

import androidx.compose.foundation.background
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import app.dreamjournal.ui.theme.mocha_overlay2
import app.dreamjournal.ui.theme.mocha_text

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
) {
    val navbarBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navbarBackStackEntry?.destination?.route

    NavigationBar {
        bottomNavBarItems.forEach { item ->
            val isSelected = currentRoute == item.route

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            if (currentRoute != null) {
                                popUpTo(route = currentRoute)
                            }
                        }
                    }
                },
                icon = {
                    Icon(
                        modifier = Modifier.background(color = Color.Transparent),
                        imageVector = ImageVector.vectorResource(item.icon),
                        contentDescription = item.name,
                        tint = if (isSelected) { mocha_text } else { mocha_overlay2  }
                    )
                },
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
            )
        }
    }
}
