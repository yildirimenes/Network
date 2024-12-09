package group.beymen.network.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import group.beymen.network.ui.main.UiConfigurationState
import group.beymen.network.data.model.main.BottomBarModel

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    configuration: MutableState<UiConfigurationState>,
    currentLanguage: String,
    onLanguageChange: (String) -> Unit,
    startDestination: String = BottomBarModel.Home.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        addHomeGraph(navController, configuration)
        addOutletGraph(navController)
        addFavoriteGraph(navController, configuration)
        addAccountGraph(
            navController = navController,
            currentLanguage = currentLanguage,
            onLanguageChange = onLanguageChange
        )
        addProductListGraph(navController)
        addProductDetailGraph(navController)
    }
}
