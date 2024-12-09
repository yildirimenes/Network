package group.beymen.network.ui.productlist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import coil.compose.AsyncImage
import group.beymen.network.R
import group.beymen.network.data.model.productlist.Product
import group.beymen.network.ui.theme.PriceRedColor
import group.beymen.network.util.AddToCartNotificationWorker
import group.beymen.network.util.Constants.IMAGE_URL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListBottomSheet(
    product: Product,
    onClose: () -> Unit,
    clickedProductId: Int
) {
    val selectedColor = remember {
        mutableStateOf(
            product.OtherProductImages?.find { it.ProductId == clickedProductId }
                ?: product.OtherProductImages?.firstOrNull()
        )
    }
    val context = LocalContext.current
    val displayedProduct = remember(selectedColor.value) { mutableStateOf(product) }
    val selectedSize = remember { mutableStateOf<String?>(null) }
    val isAddToCartEnabled = remember(selectedColor.value, selectedSize.value) {
        selectedSize.value != null
    }

    LaunchedEffect(selectedColor.value) {
        selectedColor.value?.ProductId?.let {
            selectedSize.value = null
        }
    }

    ModalBottomSheet(onDismissRequest = { onClose() }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = selectedColor.value?.ColorName?.let { "${displayedProduct.value.DisplayName} - $it" }
                    ?: displayedProduct.value.DisplayName,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = R.string.label_price, product.LabelPrice),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )

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
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontSize = 16.sp,
                            color = Color.Black
                        ),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = stringResource(id = R.string.promoted_price, it.PromotedPrice),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = PriceRedColor,
                        ),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(product.OtherProductImages ?: emptyList()) { image ->
                    val isSelected = selectedColor.value == image

                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .border(
                                2.dp,
                                if (isSelected) Color.Black else Color.Gray,
                                shape = (RectangleShape)
                            )
                            .background(
                                if (isSelected) Color.LightGray else Color.Transparent
                            )
                            .clickable {
                                selectedColor.value = image
                            }
                            .padding(top = 4.dp, bottom = 4.dp)
                    ) {
                        AsyncImage(
                            model = "${IMAGE_URL}${image.CdnPath}",
                            contentDescription = image.ColorName,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = stringResource(id = R.string.select_size), style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(4.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(displayedProduct.value.VariantWithStockList) { variant ->
                    val isAvailable = variant.StockExists
                    val isSelected = selectedSize.value == variant.ValueText

                    Box(
                        modifier = Modifier
                            .width(IntrinsicSize.Min)
                            .height(50.dp)
                            .border(
                                1.dp,
                                if (isSelected) Color.Black else if (isAvailable) Color.Gray else Color.LightGray,
                                RoundedCornerShape(4.dp)
                            )
                            .background(
                                if (isSelected) Color.Black else if (isAvailable) Color.White else Color.LightGray
                            )
                            .clickable(enabled = isAvailable) {
                                selectedSize.value = variant.ValueText
                            }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = variant.ValueText,
                            textAlign = TextAlign.Center,
                            color = if (isSelected) Color.White else if (isAvailable) Color.Black else Color.Gray,
                            style = if (!isAvailable) {
                                MaterialTheme.typography.bodyMedium.copy(textDecoration = TextDecoration.LineThrough)
                            } else {
                                MaterialTheme.typography.bodyMedium
                            },
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    selectedSize.let {
                        val displayName = product.DisplayName
                        val workRequest = OneTimeWorkRequestBuilder<AddToCartNotificationWorker>()
                            .setInputData(
                                workDataOf("displayName" to displayName)
                            )
                            .build()
                        WorkManager.getInstance(context).enqueue(workRequest)
                        onClose()
                    }
                },
                shape = RectangleShape,
                enabled = isAddToCartEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isAddToCartEnabled) Color.Black else Color.LightGray,
                    contentColor = if (isAddToCartEnabled) Color.White else Color.DarkGray
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
            ) {
                Text(text = stringResource(id = R.string.add_to_cart))
            }
        }
    }
}
