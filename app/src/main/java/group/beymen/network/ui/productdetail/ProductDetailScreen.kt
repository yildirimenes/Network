package group.beymen.network.ui.productdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import group.beymen.network.data.model.productlist.Product
import group.beymen.network.ui.components.LoadingBarComponents
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: Int,
    viewModel: ProductDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val state by viewModel.productDetailState.collectAsState()
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(productId) {
        viewModel.getProductDetail(productId)
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            // BottomSheet Content
            state?.let { productState ->
                if (productState is ProductDetailState.Success) {
                    val product = productState.product
                    product?.let {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(2.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = product.DisplayName,
                                    style = androidx.wear.compose.material.MaterialTheme.typography.body2,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                )
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "${product.LabelPrice} TL",
                                style = androidx.wear.compose.material.MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
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
                                        style = androidx.wear.compose.material.MaterialTheme.typography.body2.copy(
                                            fontSize = 10.sp,
                                            color = Color.Black
                                        ),
                                        modifier = Modifier.align(Alignment.CenterVertically)
                                    )

                                    Spacer(modifier = Modifier.width(4.dp))

                                    Text(
                                        text = "${it.PromotedPrice} TL",
                                        style = androidx.wear.compose.material.MaterialTheme.typography.body2.copy(
                                            fontSize = 12.sp,
                                            color = Color.Red,
                                            fontWeight = FontWeight.Bold
                                        ),
                                        modifier = Modifier.align(Alignment.CenterVertically)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(200.dp))


                        }
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            (state as? ProductDetailState.Success)?.product?.let { product ->
                                Text(
                                    text = product.DisplayName,
                                    style = MaterialTheme.typography.titleMedium,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            },
            content = { paddingValues ->
                when (state) {
                    is ProductDetailState.Loading -> {
                        LoadingBarComponents()
                    }
                    is ProductDetailState.Success -> {
                        val product = (state as ProductDetailState.Success).product
                        product?.let {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(paddingValues)
                            ) {
                                // Image Slider (70% of the screen)
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(10f)
                                ) {
                                    val pagerState = rememberPagerState(
                                        pageCount = { product.Images?.size ?: 0 }
                                    )

                                    HorizontalPager(
                                        state = pagerState,
                                        modifier = Modifier.fillMaxSize()
                                    ) { page ->
                                        AsyncImage(
                                            model = product.Images?.get(page),
                                            contentDescription = null,
                                            contentScale = ContentScale.FillBounds, // Fill the area while maintaining aspect ratio
                                            modifier = Modifier.fillMaxSize()
                                        )
                                    }

                                    // Page Indicator
                                    Row(
                                        modifier = Modifier
                                            .align(Alignment.BottomCenter)
                                            .padding(8.dp),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        repeat(product.Images?.size ?: 0) { index ->
                                            Box(
                                                modifier = Modifier
                                                    .size(8.dp)
                                                    .clip(CircleShape)
                                                    .background(
                                                        if (pagerState.currentPage == index) Color.Black else Color.Gray
                                                    )
                                            )
                                        }
                                    }
                                }

                                // Draggable Spacer (10% of the screen)
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f)
                                        .pointerInput(Unit) {
                                            detectTapGestures(
                                                onTap = {
                                                    coroutineScope.launch {
                                                        bottomSheetState.show()
                                                    }
                                                }
                                            )
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .width(50.dp)
                                            .height(6.dp)
                                            .background(Color.Gray, shape = RoundedCornerShape(3.dp))
                                    )
                                }

                                // Add to Cart Section (20% of the screen)
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(2f)
                                        .background(Color.White),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 8.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        TextButton(
                                            onClick = {
                                                coroutineScope.launch {
                                                    bottomSheetState.show()
                                                }
                                            },
                                            modifier = Modifier
                                                .border(1.dp, Color.Gray, RectangleShape)
                                                .padding(horizontal = 8.dp)
                                                .fillMaxWidth(0.2f)

                                        ) {
                                            Text(text = "Beden")
                                            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                                        }

                                        Button(
                                            onClick = { /* Handle Add to Cart */ },
                                            shape = RectangleShape,
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color.Black,
                                                contentColor = Color.White
                                            ),
                                            modifier = Modifier
                                                .fillMaxWidth(0.9f)
                                                .height(48.dp)
                                        ) {
                                            Text("SEPETE EKLE")
                                        }
                                    }
                                }
                            }
                        }
                    }
                    is ProductDetailState.Error -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = (state as ProductDetailState.Error).message,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailBottomSheet(
    product: Product?,
    onClose: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = { onClose() }
    ) {
        product?.let {
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

                product.ProductPromotion?.let {
                    Text(
                        text = "Kampanyalı Fiyat: ${it.PromotedPrice} TL",
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.Red)
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
                    items(product.VariantWithStockList ?: emptyList()) { variant ->
                        val isAvailable = variant.StockExists

                        Box(
                            modifier = Modifier
                                .border(
                                    1.dp,
                                    if (isAvailable) Color.Gray else Color.LightGray,
                                    RoundedCornerShape(4.dp)
                                )
                                .padding(8.dp)
                        ) {
                            Text(
                                text = variant.ValueText,
                                style = if (!isAvailable) {
                                    MaterialTheme.typography.bodyMedium.copy(
                                        //textDecoration = TextDecoration.LineThrough
                                    )
                                } else {
                                    MaterialTheme.typography.bodyMedium
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

