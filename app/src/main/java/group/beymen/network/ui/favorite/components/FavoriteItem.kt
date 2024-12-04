package group.beymen.network.ui.favorite.components

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.MaterialTheme
import coil.compose.AsyncImage
import group.beymen.network.R
import group.beymen.network.data.model.productlist.Product
import group.beymen.network.ui.components.DeleteAlertDialog
import group.beymen.network.ui.productlist.components.ProductImageSliders
import group.beymen.network.ui.productlist.components.ProductListBottomSheet
import group.beymen.network.ui.theme.PriceRedColor

@Composable
fun FavoriteItem(
    product: Product,
    isFavorite: Boolean,
    onProductClick: () -> Unit,
    onFavoriteClick: () -> Unit,
) {
    val showBottomSheet = remember { mutableStateOf(false) }
    val showAlertDialog = remember { mutableStateOf(false) }
    val classificationImageUrl = product.Classifications.firstOrNull()?.ImageUrl

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
            ProductImageSliders(mediaList = product.MediaList ?: emptyList())

            classificationImageUrl?.let { imageUrl ->
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .size(60.dp)
                        .padding(2.dp)
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
                    showAlertDialog.value = true
                },
                modifier = Modifier
                    .padding(4.dp)
                    .size(32.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.trash_can),
                    contentDescription = stringResource(id = R.string.remove_item_desc),
                    Modifier.size(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = stringResource(id = R.string.label_price, product.LabelPrice),
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
                    text = stringResource(id = R.string.promoted_price, it.PromotedPrice),
                    style = MaterialTheme.typography.body2.copy(
                        fontSize = 12.sp,
                        color = PriceRedColor,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }

        product.let {
            Row(
                modifier = Modifier
                    .padding(4.dp)
                    .padding(horizontal = 4.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = stringResource(id = R.string.product_id, it.ID.toString()),
                    style = MaterialTheme.typography.body2.copy(
                        fontSize = 10.sp,
                        color = Color.Black
                    ),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }

    }

    if (showBottomSheet.value) {
        ProductListBottomSheet(
            product = product,
            onClose = { showBottomSheet.value = false }
        )
    }

    DeleteAlertDialog(
        showDialog = showAlertDialog,
        message = stringResource(id = R.string.remove_item_question),
        onConfirm = { onFavoriteClick() },
        onDismiss = { showAlertDialog.value = false }
    )
}


