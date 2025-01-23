package app.dreamjournal.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import app.dreamjournal.ui.theme.mocha_base
import app.dreamjournal.ui.theme.mocha_mauve
import app.dreamjournal.ui.theme.mocha_surface0
import app.dreamjournal.ui.theme.mocha_text

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
) {
    val navbarBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navbarBackStackEntry?.destination?.route

    val labelFontSize = 12.sp
    val labelFontWeight = FontWeight.SemiBold
    val labelColor = mocha_text

    NavigationBar(
        containerColor = mocha_surface0
    ) {
        bottomNavbarItems.forEach { item ->
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
                        imageVector = item.icon,
                        contentDescription = item.name,
                        tint = if (isSelected) {
                            mocha_base
                        } else {
                            mocha_text
                        }
                    )
                },
                label = {
                    Text(
                        text = item.name,
                        fontSize = labelFontSize,
                        fontWeight = labelFontWeight,
                        color = labelColor
                    )
                },
                colors = NavigationBarItemDefaults.colors(indicatorColor = mocha_mauve)
            )
        }
    }
}
