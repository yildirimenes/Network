package group.beymen.network.ui.homepage.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import group.beymen.network.data.model.homepage.HomeItemModel
import group.beymen.network.ui.components.HomeImageSlider
import group.beymen.network.ui.components.NetworkImageComponents

@Composable
fun HomeContent(
    items: List<HomeItemModel>,
    onClickItem: (productId: Int?, categoryId: Int?, webUrl: String?) -> Unit,
    paddingValues: PaddingValues = PaddingValues()
) {
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
        items(items) { item ->
            when (item.Code) {
                "vitrin-slider" -> {
                    val vitrinSliderItems = item.ItemList ?: emptyList()
                    if (vitrinSliderItems.isNotEmpty()) {
                        HomeImageSlider(
                            itemList = vitrinSliderItems,
                            duration = item.Duration ?: 5000,
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
                            duration = item.Duration ?: 5000,
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

