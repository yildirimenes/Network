package group.beymen.network.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import group.beymen.network.common.UiConfigurationState

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    configuration: MutableState<UiConfigurationState>,
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route,
        modifier = modifier
    ) {
        addHomeGraph(navController, configuration)
        addOutletGraph(navController)
        addAccountGraph(navController, configuration)
    }
}
