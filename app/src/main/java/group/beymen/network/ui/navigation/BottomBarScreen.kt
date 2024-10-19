package group.beymen.network.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object Home : BottomBarScreen(
        route = "HOME",
        title = "HOME",
        icon = Icons.Default.Home
    )

    data object Outlet : BottomBarScreen(
        route = "OUTLET",
        title = "OUTLET",
        icon = Icons.Default.Menu
    )

    data object Account : BottomBarScreen(
        route = "ACCOUNT",
        title = "ACCOUNT",
        icon = Icons.Default.AccountCircle
    )
}