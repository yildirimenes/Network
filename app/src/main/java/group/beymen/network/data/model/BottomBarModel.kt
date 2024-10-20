package group.beymen.network.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import group.beymen.network.R

sealed class BottomBarModel(
    val route: String,
    val titleResId: Int,
    val icon: ImageVector
) {
    data object Home : BottomBarModel(
        route = "HOME",
        titleResId = R.string.home_title,
        icon = Icons.Default.Home
    )

    data object Outlet : BottomBarModel(
        route = "OUTLET",
        titleResId = R.string.outlet_title,
        icon = Icons.Default.Menu
    )

    data object Account : BottomBarModel(
        route = "ACCOUNT",
        titleResId = R.string.account_title,
        icon = Icons.Default.AccountCircle
    )
}
