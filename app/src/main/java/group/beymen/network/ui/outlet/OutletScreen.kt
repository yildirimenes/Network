package group.beymen.network.ui.outlet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import group.beymen.network.R
import group.beymen.network.ui.components.CustomTopAppBar
import group.beymen.network.ui.components.ErrorComponents
import group.beymen.network.ui.components.LoadingBarComponents
import group.beymen.network.ui.components.NetworkImageComponents
import group.beymen.network.ui.main.components.BottomBarComponents

@Composable
fun OutletScreen(
    navController: NavHostController,
    viewModel: OutletViewModel = hiltViewModel(),
    onClickItem: (productId: Int?, categoryId: Int?, webUrl: String?) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.fetchOutletItems("N", "network-app-outlet")
    }

    val outletState = viewModel.outletPageState

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = stringResource(id = R.string.outlet_title),
            )
        },
        bottomBar = { BottomBarComponents(navController = navController) },
        content = { paddingValues ->
            when {
                outletState.isLoading -> {
                    LoadingBarComponents()
                }
                outletState.error != null -> {
                    ErrorComponents(
                        title = stringResource(id = R.string.app_name),
                        onRetry = { viewModel.fetchOutletItems() }
                    )
                }
                outletState.outletItems != null -> {
                    LazyColumn(
                        modifier = Modifier
                            .background(Color.White)
                            .fillMaxSize()
                            .padding(
                                top = paddingValues.calculateTopPadding(),
                                bottom = paddingValues.calculateBottomPadding(),
                                start = 8.dp,
                                end = 8.dp
                            ),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(outletState.outletItems) { item ->
                            item.ImageUrl?.let { imageUrl ->
                                NetworkImageComponents(
                                    url = imageUrl,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            onClickItem(
                                                item.ProductId?.toIntOrNull(),
                                                item.CategoryID?.toIntOrNull(),
                                                item.Link
                                            )
                                        }
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}
