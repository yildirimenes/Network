package group.beymen.network.ui.productlist.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.MaterialTheme
import coil.compose.AsyncImage
import group.beymen.network.R
import group.beymen.network.data.model.productlist.Product

@Composable
fun ProductItem(
    product: Product,
    //viewModel: FavoriteViewModel, // Pass FavoriteViewModel here
    onProductClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    val showBottomSheet = remember { mutableStateOf(false) }
    val classificationImageUrl = product.Classifications.firstOrNull()?.ImageUrl
    val isFavorited = remember { mutableStateOf(false) } // Track favorite state (replace with actual state in ViewModel)

    Column(
        modifier = Modifier
            .padding(start = 4.dp, end = 4.dp)
            .clickable { onProductClick() }
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            ImageSliders(mediaList = product.MediaList ?: emptyList())

            classificationImageUrl?.let { imageUrl ->
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Classification Image",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .size(60.dp)
                        .padding(2.dp)
                )
            }

            IconButton(
                onClick = { showBottomSheet.value = true },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
                    .size(32.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_quick_add_basket),
                    contentDescription = "Show Picker",
                    tint = Color.LightGray,
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = product.DisplayName,
                style = MaterialTheme.typography.body2,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = {
                    /*
                    isFavorited.value = !isFavorited.value
                    if (isFavorited.value) {
                        viewModel.addFavorite(product.toFavoriteProduct())
                    } else {
                        viewModel.removeFavorite(product.ID)
                    }*/
                },
                modifier = Modifier
                    //.align(Alignment.TopEnd)
                    .padding(4.dp)
                    .size(32.dp)
            ) {
                Icon(
                    imageVector = if (isFavorited.value) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Add to Favorites"
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "${product.LabelPrice} TL",
            style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        product.ProductPromotion?.let {
            Row(
                modifier = Modifier
                    .padding(4.dp)
                    .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = it.CampaignTitle,
                    style = MaterialTheme.typography.body2.copy(
                        fontSize = 10.sp,
                        color = Color.Black
                    ),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = "${it.PromotedPrice} TL",
                    style = MaterialTheme.typography.body2.copy(
                        fontSize = 12.sp,
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }

    if (showBottomSheet.value) {
        ProductDetailBottomSheet(
            product = product,
            onClose = { showBottomSheet.value = false }
        )
    }
}