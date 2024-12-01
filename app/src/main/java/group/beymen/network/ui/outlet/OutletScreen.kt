package group.beymen.network.ui.outlet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import group.beymen.network.R
import group.beymen.network.ui.components.LoadingBarComponents
import group.beymen.network.ui.components.NetworkImageComponents
import group.beymen.network.ui.main.components.BottomBarComponents

@OptIn(ExperimentalMaterial3Api::class)
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
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.outlet_title),
                            fontWeight = FontWeight.W400,
                            fontSize = 28.sp
                        )
                    }
                },
            )
        },
        bottomBar = { BottomBarComponents(navController = navController) },
        content = { paddingValues ->
            when {
                outletState.isLoading -> {
                    LoadingBarComponents()
                }
                outletState.error != null -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = stringResource(id = R.string.error_message, outletState.error))
                    }
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
