package group.beymen.network.ui.productlist.components

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import group.beymen.network.data.model.productlist.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailBottomSheet(
    product: Product,
    onClose: () -> Unit
) {
    val selectedSize = remember { mutableStateOf<String?>(null) }
    val isAddToCartEnabled = selectedSize.value != null

    ModalBottomSheet(
        onDismissRequest = { onClose() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = product.DisplayName,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "${product.LabelPrice} TL",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))

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
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 10.sp,
                            color = Color.Black
                        ),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "${it.PromotedPrice} TL",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 12.sp,
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }

            product.OtherProductImages?.firstOrNull()?.ColorName?.let { colorName ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Renk: $colorName",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyRow {
                items(product.MediaList ?: emptyList()) { media ->
                    AsyncImage(
                        model = media.ResizedUrlPath,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .padding(4.dp)
                            .border(1.dp, Color.Gray)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Beden Seç:", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(4.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(product.VariantWithStockList) { variant ->
                    val isAvailable = variant.StockExists
                    val isSelected = selectedSize.value == variant.ValueText // Seçili durum

                    Box(
                        modifier = Modifier
                            .size(50.dp)
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
                            .padding(8.dp)
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
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /* Handle Add to Cart */ },
                enabled = isAddToCartEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isAddToCartEnabled) Color.Black else Color.LightGray,
                    contentColor = if (isAddToCartEnabled) Color.White else Color.DarkGray
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 0.dp
                )
            ) {
                Text(text = "Sepete Ekle")
            }
        }
    }
}
