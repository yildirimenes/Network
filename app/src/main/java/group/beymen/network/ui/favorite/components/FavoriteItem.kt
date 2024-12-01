package group.beymen.network.ui.favorite.components

import androidx.compose.foundation.background
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
import androidx.compose.material.AlertDialog
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.MaterialTheme
import coil.compose.AsyncImage
import group.beymen.network.R
import group.beymen.network.data.model.productlist.Product
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

            IconButton(
                onClick = { showBottomSheet.value = true },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
                    .size(32.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_quick_add_basket),
                    contentDescription = "",
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
    }

    if (showBottomSheet.value) {
        ProductListBottomSheet(
            product = product,
            onClose = { showBottomSheet.value = false }
        )
    }

    CustomAlertDialog(
        showDialog = showAlertDialog,
        message = stringResource(id = R.string.remove_item_question),
        onConfirm = { onFavoriteClick() },
        onDismiss = { showAlertDialog.value = false }
    )
}

@Composable
fun CustomAlertDialog(
    showDialog: MutableState<Boolean>,
    titleContent: @Composable (() -> Unit)? = null,
    message: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    titleContent?.invoke() ?: androidx.compose.material.Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = stringResource(id = R.string.confirmation_icon),
                        tint = Color.LightGray,
                        modifier = Modifier.size(50.dp)
                    )
                }
            },
            text = {
                androidx.compose.material.Text(
                    text = message,
                    textAlign = TextAlign.Center,
                    style = androidx.compose.material.MaterialTheme.typography.body1,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .fillMaxWidth()
                )
            },
            confirmButton = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(Color.Black, shape = RoundedCornerShape(10.dp))
                            .shadow(4.dp, shape = RoundedCornerShape(10.dp))
                            .clickable {
                                onConfirm()
                                showDialog.value = false
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        androidx.compose.material.Text(
                            text = stringResource(id = R.string.confirm),
                            color = Color.White,
                            modifier = Modifier.padding(vertical = 12.dp),
                            style = androidx.compose.material.MaterialTheme.typography.button
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(Color.White, shape = RoundedCornerShape(10.dp))
                            .border(1.dp, Color.Black, shape = RoundedCornerShape(10.dp))
                            .clickable {
                                onDismiss()
                                showDialog.value = false
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        androidx.compose.material.Text(
                            text = stringResource(id = R.string.dismiss),
                            color = Color.Black,
                            modifier = Modifier.padding(vertical = 12.dp),
                            style = androidx.compose.material.MaterialTheme.typography.button
                        )
                    }
                }
            },
            shape = RoundedCornerShape(10.dp),
            backgroundColor = Color.White
        )
    }
}

