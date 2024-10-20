package group.beymen.network.ui.navigation

import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import group.beymen.network.common.UiConfigurationState
import group.beymen.network.ui.account.AccountScreen
import group.beymen.network.ui.homepage.HomePageScreen
import group.beymen.network.ui.outlet.OutletScreen


fun NavGraphBuilder.addHomeGraph(
    navController: NavHostController,
    configuration: MutableState<UiConfigurationState>
) {
    composable(route = BottomBarScreen.Home.route) {
        HomePageScreen(navController)
    }
}

fun NavGraphBuilder.addOutletGraph(navController: NavHostController) {
    composable(route = BottomBarScreen.Outlet.route) {
        OutletScreen(navController)
    }
}

fun NavGraphBuilder.addAccountGraph(
    navController: NavHostController,
    configuration: MutableState<UiConfigurationState>) {
    composable(route = BottomBarScreen.Account.route) {
        AccountScreen(navController)
    }
}

