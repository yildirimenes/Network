package group.beymen.network.ui.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import group.beymen.network.R
import group.beymen.network.data.mapper.toProduct
import group.beymen.network.ui.components.CustomTopAppBar
import group.beymen.network.ui.favorite.components.FavoriteItem

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel,
    onBackClick: () -> Unit

) {
    val favorites by viewModel.favorites.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomTopAppBar(
                title = stringResource(id = R.string.favorites),
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (favorites.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.no_items_favorite_message),
                            style = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Center)
                        )
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(
                            start = 4.dp,
                            end = 4.dp,
                            top = 4.dp,
                            bottom = 72.dp
                            //bottom = paddingValues.calculateBottomPadding()
                        ),
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        items(favorites) { favorite ->
                            FavoriteItem(
                                product = favorite.toProduct(),
                                isFavorite = true,
                                onFavoriteClick = { viewModel.removeFavorite(favorite) }
                            )
                        }
                    }
                }
            }
        }
    )
}
