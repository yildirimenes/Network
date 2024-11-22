package group.beymen.network.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import group.beymen.network.data.model.homepage.HomePageContent
import kotlinx.coroutines.delay

@Composable
fun ImageSlider(
    itemList: List<HomePageContent>,
    duration: Int,
    onClickItem: (productId: Int?, categoryId: Int?, webUrl: String?) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { itemList.size })

    LaunchedEffect(Unit) {
        while (true) {
            delay(duration.toLong())
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.scrollToPage(nextPage)
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .background(Color.White)
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { currentPage ->
                val currentItem = itemList[currentPage]
                currentItem.ImageUrl?.let { imageUrl ->
                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                onClickItem(
                                    currentItem.ProductId?.toIntOrNull(),
                                    currentItem.CategoryID?.toIntOrNull(),
                                    currentItem.Link
                                )
                            },
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 8.dp
                        ),
                        shape = RoundedCornerShape(0.dp)
                    ) {
                        NetworkImageComponents(
                            url = imageUrl,
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                        )
                    }
                }
            }
        }

        PageIndicator(
            pageCount = itemList.size,
            currentPage = pagerState.currentPage,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp)
        )
    }
}

@Composable
fun PageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        repeat(pageCount) {
            IndicatorDots(isSelected = it == currentPage, modifier = Modifier.size(10.dp, 3.dp))
        }
    }
}

@Composable
fun IndicatorDots(isSelected: Boolean, modifier: Modifier = Modifier) {
    val color = if (isSelected) Color.DarkGray else Color.LightGray
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(2.dp))
            .background(color)
    )
}