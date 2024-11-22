package group.beymen.network.ui.homepage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import group.beymen.network.ui.components.ImageSlider
import group.beymen.network.ui.components.LoadingBarComponents
import group.beymen.network.ui.components.NetworkImageComponents
import group.beymen.network.util.Resource

@Composable
fun HomePageScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onClickItem: (productId: Int?, categoryId: Int?, webUrl: String?) -> Unit
) {
    val homePageState = homeViewModel.mainPageState.value

    when (homePageState) {
        is Resource.Loading -> {
            LoadingBarComponents()
        }
        is Resource.Success -> {
            homePageState.data?.Result?.let { result ->
                LazyColumn(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize()
                        .padding(top = 110.dp, bottom = 110.dp, start = 8.dp, end = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Vitrin Slider için ImageSlider
                    val vitrinSliderItems = result.filter { it.Code == "vitrin-slider" }
                        .flatMap { it.ItemList ?: emptyList() }

                    if (vitrinSliderItems.isNotEmpty()) {
                        item {
                            ImageSlider(
                                itemList = vitrinSliderItems,
                                duration = 3000,
                                onClickItem = { productId, categoryId, webUrl ->
                                    onClickItem(productId, categoryId, webUrl)
                                }
                            )
                        }
                    }

                    // Diğer List Tipindeki Resimler için LazyColumn item'ları
                    val listItems = result.filter { it.Type == "List" }
                    items(listItems) { item ->
                        item.ImageUrl?.let { imageUrl ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(450.dp)
                                    .clickable {
                                        onClickItem(
                                            item.ProductId?.toIntOrNull(),
                                            item.CategoryID?.toIntOrNull(),
                                            item.Link
                                        )
                                    }
                            ) {
                                NetworkImageComponents(
                                    url = imageUrl,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }
                }
            }
        }
        is Resource.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "${homePageState.message}")
            }
        }
    }
}