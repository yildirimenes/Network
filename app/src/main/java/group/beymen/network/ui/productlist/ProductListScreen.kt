package group.beymen.network.ui.productlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import group.beymen.network.ui.components.LoadingBarComponents
import group.beymen.network.ui.productlist.components.ProductItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    navController: NavHostController,
    categoryId: Int,
    viewModel: ProductListViewModel = hiltViewModel(),
    onProductClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(categoryId) {
        viewModel.loadProducts(categoryId)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    val categoryName = state.productModel?.CategoryName ?: "Products"
                    val totalItemCount = state.productModel?.TotalItemCount ?: 0
                    Text("$categoryName [$totalItemCount]", fontSize = 14.sp)
                },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back")
                    }
                }
            )
        },
        content = { padding ->
            when {
                state.isLoading -> LoadingBarComponents()
                state.errorMessage != null -> Text(
                    text = state.errorMessage!!,
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center),
                    textAlign = TextAlign.Center
                )
                else -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                    ) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = padding,
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            itemsIndexed(state.products) { index, product ->
                                ProductItem(
                                    product = product,
                                    isFavorite = viewModel.isFavorite(product.ID),
                                    onProductClick = { onProductClick(product.ID) },
                                    onFavoriteClick = { viewModel.toggleFavorite(product) }
                                )

                                if (index == state.products.lastIndex) {
                                    viewModel.loadNextPage(categoryId)
                                }
                            }
                        }

                        if (state.isLoading) {
                            LoadingBarComponents()
                        }
                    }
                }
            }
        }
    )
}