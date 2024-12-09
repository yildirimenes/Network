package group.beymen.network.ui.homepage

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import group.beymen.network.R
import group.beymen.network.ui.components.ErrorComponents
import group.beymen.network.ui.components.HomeImageSlider
import group.beymen.network.ui.components.LoadingBarComponents
import group.beymen.network.ui.components.NetworkImageComponents
import group.beymen.network.ui.main.components.BottomBarComponents
import group.beymen.network.data.util.Resource
import group.beymen.network.ui.components.CustomTopAppBar

@Composable
fun HomePageScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onClickItem: (productId: Int?, categoryId: Int?, webUrl: String?) -> Unit
) {
    val state by homeViewModel.mainPageState.collectAsState()

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = stringResource(id = R.string.app_name),
            )
        },
        bottomBar = { BottomBarComponents(navController = navController) },
        content = { paddingValues ->
            when (state) {
                is Resource.Loading -> {
                    LoadingBarComponents()
                }
                is Resource.Success -> {
                    state.data?.Result?.let { result ->
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
                            items(result) { item ->
                                when (item.Code) {
                                    "vitrin-slider" -> {
                                        val vitrinSliderItems = item.ItemList ?: emptyList()
                                        if (vitrinSliderItems.isNotEmpty()) {
                                            HomeImageSlider(
                                                itemList = vitrinSliderItems,
                                                duration = item.Duration?:5000,
                                                onClickItem = { productId, categoryId, webUrl ->
                                                    onClickItem(productId, categoryId, webUrl)
                                                }
                                            )
                                        }
                                    }

                                    "orta-slider" -> {
                                        val ortaSliderItems = item.ItemList ?: emptyList()
                                        if (ortaSliderItems.isNotEmpty()) {
                                            HomeImageSlider(
                                                itemList = ortaSliderItems,
                                                duration = item.Duration?:5000,
                                                onClickItem = { productId, categoryId, webUrl ->
                                                    onClickItem(productId, categoryId, webUrl)
                                                }
                                            )
                                        }
                                    }

                                    else -> {
                                        if (item.Type == "List") {
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
                    }
                }
                is Resource.Error -> {
                    ErrorComponents(
                        title = stringResource(id = R.string.app_name),
                        onRetry = { homeViewModel.getLoadPage() }
                    )
                }
            }
        }
    )
}
