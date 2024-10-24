package group.beymen.network.ui.homepage

import androidx.compose.foundation.background
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
fun HomePageScreen(navController: NavHostController, homeViewModel: HomeViewModel = hiltViewModel()) {
    val mainPageState = homeViewModel.mainPageState.value


    when (mainPageState) {
        is Resource.Loading -> {
            LoadingBarComponents()
        }
        is Resource.Success -> {
            mainPageState.data?.Result?.let { result ->
                LazyColumn(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize()
                        .padding(top = 110.dp, bottom = 110.dp, start = 8.dp, end = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Vitrin Slider için ImageSlider
                    val vitrinSliderItems = result.filter { it.Code == "vitrin-slider" }
                    vitrinSliderItems.forEach { sliderItem ->
                        sliderItem.ItemList?.let { itemList ->
                            sliderItem.Duration?.let { duration ->
                                item {
                                    ImageSlider(itemList = itemList, duration = duration)
                                }
                            }
                        }
                    }

                    val listItems = result.filter { it.Type == "List" }
                    // Diğer List Tipindeki Resimler için LazyColumn item'ları
                    items(listItems) { item ->
                        item.ImageUrl?.let {
                            NetworkImageComponents(
                                url = it,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(400.dp)
                            )
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
                Text(text = "${mainPageState.message}")
            }
        }
    }
}