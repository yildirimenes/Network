package group.beymen.network.ui.productdetail

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import group.beymen.network.R
import group.beymen.network.ui.components.ErrorComponents
import group.beymen.network.ui.components.LoadingBarComponents
import group.beymen.network.ui.productdetail.components.ExpandableCardContent
import group.beymen.network.ui.theme.PriceRedColor
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

    var isExpanded by remember { mutableStateOf(false) }

    var selectedSize by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(productId) {
        viewModel.getProductDetail(productId)
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            state.let { productState ->
                if (productState is ProductDetailState.Success) {
                    val product = productState.product
                    product?.Sizes?.let { sizes ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 300.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.select_size),
                                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentWidth(Alignment.Start)
                                        .padding(bottom = 8.dp)
                                )

                                LazyColumn(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    items(sizes) { size ->
                                        Column(
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                verticalAlignment = Alignment.Top,
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                TextButton(
                                                    onClick = {
                                                        if (!size.NoStock) {
                                                            selectedSize = size.ValueText
                                                            coroutineScope.launch { bottomSheetState.hide() }
                                                        }
                                                    },
                                                    enabled = !size.NoStock,
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .wrapContentWidth(Alignment.Start)
                                                ) {
                                                    Text(
                                                        text = size.ValueText,
                                                        style = MaterialTheme.typography.bodySmall.copy(
                                                            fontSize = 12.sp,
                                                            color = if (size.NoStock) Color.Gray else Color.Black
                                                        )
                                                    )
                                                }
                                                if (size.NoStock) {
                                                    Row(
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        modifier = Modifier
                                                            .wrapContentWidth(Alignment.End)
                                                    ) {
                                                        Icon(
                                                            imageVector = Icons.Default.Info,
                                                            contentDescription = "",
                                                            tint = Color.Gray,
                                                            modifier = Modifier.size(16.dp)
                                                        )
                                                        Spacer(modifier = Modifier.width(2.dp))
                                                        TextButton(
                                                            onClick = { },
                                                            modifier = Modifier
                                                        ) {
                                                            Text(
                                                                text = stringResource(id = R.string.out_of_stock_notify),
                                                                style = MaterialTheme.typography.bodySmall.copy(
                                                                    fontSize = 12.sp,
                                                                    color = Color.Gray
                                                                ),
                                                                textDecoration = TextDecoration.Underline

                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                            Divider(
                                                color = Color.LightGray,
                                                thickness = 1.dp,
                                                modifier = Modifier.padding(vertical = 4.dp)
                                            )
                                        }
                                    }
                                }
                            }
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
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(12f)
                                ) {
                                    val pagerState = rememberPagerState(
                                        pageCount = { product.Images?.size ?: 0 }
                                    )

                                    product.Badges?.firstOrNull()?.let { badge ->
                                        AsyncImage(
                                            model = badge.ImageUrl,
                                            contentDescription = "Badge",
                                            modifier = Modifier
                                                .size(100.dp)
                                                .align(Alignment.TopStart)
                                                .padding(4.dp)
                                                .zIndex(1f),
                                            contentScale = ContentScale.Fit
                                        )
                                    }


                                    HorizontalPager(
                                        state = pagerState,
                                        modifier = Modifier.fillMaxSize()
                                    ) { page ->
                                        AsyncImage(
                                            model = product.Images?.get(page),
                                            contentDescription = null,
                                            contentScale = ContentScale.FillBounds,
                                            modifier = Modifier.fillMaxSize(),
                                            placeholder = painterResource(id = R.drawable.placeholder),
                                            error = painterResource(id = R.drawable.placeholder)
                                        )
                                    }

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
                                                        if (pagerState.currentPage == index) Color.Gray else Color.LightGray
                                                    )
                                            )
                                        }
                                    }
                                }
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        //.weight(3f)

                                ) {
                                    Card(
                                        shape = RectangleShape,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .animateContentSize()
                                            .align(Alignment.CenterHorizontally),
                                        onClick = { isExpanded = !isExpanded },
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color.White
                                        )
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(16.dp)
                                        ) {

                                            if (!isExpanded) {
                                                Text(
                                                    text = product.DisplayName,
                                                    style = MaterialTheme.typography.titleLarge.copy(
                                                        fontWeight = FontWeight.Bold,
                                                        fontSize = 16.sp,
                                                    ),
                                                    modifier = Modifier.padding(horizontal = 4.dp)
                                                )

                                                Spacer(modifier = Modifier.height(4.dp))

                                                Text(
                                                    text = stringResource(id = R.string.label_price, product.LabelPrice),
                                                    style = MaterialTheme.typography.titleMedium.copy(
                                                        fontWeight = FontWeight.Bold,
                                                        fontSize = 16.sp,
                                                    ),
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

                                            }

                                            if (isExpanded) {
                                                ExpandableCardContent(product = product)
                                            }
                                        }
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(3f)
                                        .background(Color.White),
                                    contentAlignment = Alignment.Center
                                ) {
                                    product.Sizes?.let { sizes ->
                                        if (sizes.size == 1) {
                                            Button(
                                                onClick = { /* Handle Add to Cart */ },
                                                shape = RectangleShape,
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = Color.Black,
                                                    contentColor = Color.White
                                                ),
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(16.dp)
                                                    .height(48.dp)
                                            ) {
                                                Text(text = stringResource(id = R.string.add_to_cart))
                                            }
                                        } else {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(horizontal = 8.dp),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                TextButton(
                                                    onClick = {
                                                        coroutineScope.launch { bottomSheetState.show() }
                                                    },
                                                    modifier = Modifier
                                                        .border(1.dp, Color.Gray, RectangleShape)
                                                        .padding(horizontal = 2.dp)
                                                        .fillMaxWidth(0.2f)
                                                ) {
                                                    Text(text = selectedSize ?: stringResource(id = R.string.selected_size))
                                                    Icon(
                                                        Icons.Default.ArrowDropDown,
                                                        contentDescription = null,
                                                        Modifier.size(30.dp)
                                                    )
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
                                                    Text(text = stringResource(id = R.string.add_to_cart))
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    is ProductDetailState.Error -> {
                        ErrorComponents(
                            title = stringResource(id = R.string.app_name),
                            onRetry = { viewModel.getProductDetail(productId) }
                        )
                    }
                }
            }
        )
    }
}



