package group.beymen.network.ui.navigation

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import group.beymen.network.common.UiConfigurationState
import group.beymen.network.ui.account.AccountScreen
import group.beymen.network.data.model.main.BottomBarModel
import group.beymen.network.ui.homepage.HomePageScreen
import group.beymen.network.ui.outlet.OutletScreen
import group.beymen.network.ui.productlist.ProductListScreen

fun NavGraphBuilder.addHomeGraph(
    navController: NavHostController,
    configuration: MutableState<UiConfigurationState>,
) {
    composable(
        route = BottomBarModel.Home.route,
    ) {
        HomePageScreen(
            navController = navController,
            onClickItem = { productId, categoryId, webUrl ->
                when {
                    productId != null -> {
                        navController.navigate("productList/$productId")
                    }
                    categoryId != null -> {
                        navController.navigate("productList/$categoryId")
                    }
                    webUrl != null -> {
                        navController.navigate("webView?url=${Uri.encode(webUrl)}")
                    }
                }
            }
        )
    }
}

fun NavGraphBuilder.addProductListGraph(
    navController: NavHostController
) {
    composable(
        route = "productList/{categoryId}",
        arguments = listOf(navArgument("categoryId") { type = NavType.IntType })
    ) { backStackEntry ->
        val categoryId = backStackEntry.arguments?.getInt("categoryId") ?: 0
        ProductListScreen(
            categoryId = categoryId,
            onProductClick = { productId ->
                navController.navigate("productDetail/$productId")
            }
        )
    }
}

fun NavGraphBuilder.addOutletGraph(navController: NavHostController) {
    composable(route = BottomBarModel.Outlet.route) {
        OutletScreen(
            navController = navController,
            onClickItem = { productId, categoryId, webUrl ->
                when {
                    productId != null -> {
                        navController.navigate("productList/$productId")
                    }
                    categoryId != null -> {
                        navController.navigate("productList/$categoryId")
                    }
                    webUrl != null -> {
                        navController.navigate("webView?url=${Uri.encode(webUrl)}")
                    }

                }
            }

        )
    }
}

fun NavGraphBuilder.addAccountGraph(
    navController: NavHostController,
    configuration: MutableState<UiConfigurationState>) {
    composable(route = BottomBarModel.Account.route) {
        AccountScreen(navController)
    }
}
