package group.beymen.network.ui.productdetail

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import coil.compose.AsyncImage
import group.beymen.network.R
import group.beymen.network.ui.components.ErrorComponents
import group.beymen.network.ui.components.LoadingBarComponents
import group.beymen.network.ui.productdetail.components.AddToCartSection
import group.beymen.network.ui.productdetail.components.CollapsedProductContent
import group.beymen.network.ui.productdetail.components.ExpandableCardContent
import group.beymen.network.ui.productdetail.components.SizePickerSheetContent
import group.beymen.network.util.AddToCartNotificationWorker
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: Int,
    viewModel: ProductDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val state by viewModel.productDetailState.collectAsState()
    val context = LocalContext.current
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
            val productState = state as? ProductDetailState.Success
            val product = productState?.product

            product?.Sizes?.let { sizes ->
                SizePickerSheetContent(
                    sizes = sizes,
                    selectedSize = selectedSize,
                    onSizeSelected = { size -> selectedSize = size },
                    onDismissRequest = { coroutineScope.launch { bottomSheetState.hide() } }
                )
            }
        }
    ) {
        when (state) {
            is ProductDetailState.Loading -> {
                LoadingBarComponents()
            }
            is ProductDetailState.Success -> {
                val product = (state as ProductDetailState.Success).product
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = product?.DisplayName?:"",
                                        style = MaterialTheme.typography.titleMedium,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        textAlign = TextAlign.Center
                                    )
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
                    bottomBar = {
                        AddToCartSection(
                            product = product!!,
                            selectedSize = selectedSize,
                            onSizeSelectClick = {
                                coroutineScope.launch { bottomSheetState.show() }
                            },
                            onAddToCartClick = {
                                val displayName = product?.DisplayName
                                val workRequest =
                                    OneTimeWorkRequestBuilder<AddToCartNotificationWorker>()
                                        .setInputData(
                                            workDataOf("displayName" to displayName)
                                        )
                                        .build()
                                WorkManager.getInstance(context).enqueue(workRequest)
                            }
                        )
                    },
                    content = { paddingValues ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight()
                                ) {
                                    val pagerState = rememberPagerState(
                                        pageCount = { product?.Images?.size ?: 0 }
                                    )

                                    product?.Badges?.firstOrNull()?.let { badge ->
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
                                            model = product?.Images?.get(page),
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
                                        repeat(product?.Images?.size ?: 0) { index ->
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
                                        .align(Alignment.BottomCenter)
                                        .zIndex(2f)
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
                                                CollapsedProductContent(product = product!!)
                                            }

                                            if (isExpanded) {
                                                ExpandableCardContent(product = product!!)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                )
            }
            is ProductDetailState.Error -> {
                ErrorComponents(
                    title = stringResource(id = R.string.app_name),
                    onRetry = { viewModel.getProductDetail(productId) }
                )
            }
        }
    }
}