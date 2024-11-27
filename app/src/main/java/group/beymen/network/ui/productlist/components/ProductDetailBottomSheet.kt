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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
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
    // State to hold the selected color
    val selectedColor = remember { mutableStateOf(product.OtherProductImages?.firstOrNull()) }

    // State to update product details based on the selected color
    val selectedDetails = selectedColor.value

    ModalBottomSheet(
        onDismissRequest = { onClose() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Display updated name and price based on the selected color
            Text(
                text = selectedDetails?.ColorName?.let { "${product.DisplayName} - $it" }
                    ?: product.DisplayName,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Display price information
            Text(
                text = "${product.LabelPrice} TL",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Display promotion details
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

            Spacer(modifier = Modifier.height(16.dp))

            // Display OtherProductImages in LazyRow
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(product.OtherProductImages ?: emptyList()) { image ->
                    val isSelected = selectedColor.value == image

                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .border(
                                2.dp,
                                if (isSelected) Color.Black else Color.Gray,
                                RoundedCornerShape(4.dp)
                            )
                            .background(
                                if (isSelected) Color.LightGray else Color.Transparent
                            )
                            .clickable {
                                selectedColor.value = image
                            }
                            .padding(4.dp)
                    ) {
                        AsyncImage(
                            model = "https://img-network.mncdn.com/productimages/${image.CdnPath}",
                            contentDescription = image.ColorName,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Display stock and size selection for the selected color
            Text(text = "Beden Seç:", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(4.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(product.VariantWithStockList) { variant ->
                    val isAvailable = variant.StockExists
                    val isSelected = variant.ValueText == selectedColor.value?.Style

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
                                // Select size logic (optional, depends on your requirements)
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

            // Add to Cart Button
            Button(
                onClick = { /* Handle Add to Cart */ },
                shape = RectangleShape,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
            ) {
                Text(text = "Sepete Ekle")
            }
        }
    }
}

/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailBottomSheet(
    product: Product,
    onClose: () -> Unit,
    fetchProductById: (Int) -> Product // A function to fetch product data by ProductId
) {
    // State to track the currently selected color
    val selectedColor = remember { mutableStateOf(product.OtherProductImages?.firstOrNull()) }

    // State to store the currently displayed product details
    val displayedProduct = remember(selectedColor.value) {
        mutableStateOf(product)
    }

    // State for selected size
    val selectedSize = remember { mutableStateOf<String?>(null) }

    // State to enable or disable the Add to Cart button
    val isAddToCartEnabled = remember(selectedColor.value, selectedSize.value) {
        selectedSize.value != null
    }

    // Fetch product details dynamically when a new color is selected
    LaunchedEffect(selectedColor.value) {
        selectedColor.value?.ProductId?.let { productId ->
            displayedProduct.value = fetchProductById(productId)
            selectedSize.value = null // Reset size selection when color changes
        }
    }

    ModalBottomSheet(
        onDismissRequest = { onClose() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Display updated name and price based on the selected color
            Text(
                text = selectedColor.value?.ColorName?.let { "${displayedProduct.value.DisplayName} - $it" }
                    ?: displayedProduct.value.DisplayName,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Display price
            Text(
                text = "${displayedProduct.value.LabelPrice} TL",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )

            // Display updated promotion details
            displayedProduct.value.ProductPromotion?.let {
                Spacer(modifier = Modifier.height(8.dp))
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

            Spacer(modifier = Modifier.height(16.dp))

            // LazyRow for OtherProductImages
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(product.OtherProductImages ?: emptyList()) { image ->
                    val isSelected = selectedColor.value == image

                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .border(
                                2.dp,
                                if (isSelected) Color.Black else Color.Gray,
                                RoundedCornerShape(4.dp)
                            )
                            .background(
                                if (isSelected) Color.LightGray else Color.Transparent
                            )
                            .clickable {
                                selectedColor.value = image
                            }
                            .padding(4.dp)
                    ) {
                        AsyncImage(
                            model = "https://img-network.mncdn.com/productimages/${image.CdnPath}",
                            contentDescription = image.ColorName,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Size Selection
            Text(text = "Beden Seç:", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(4.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
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

            // Add to Cart Button
            Button(
                onClick = { /* Handle Add to Cart */ },
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
                Text(text = "Sepete Ekle")
            }
        }
    }
}*/

