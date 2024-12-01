package group.beymen.network.ui.productdetail.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import group.beymen.network.R
import group.beymen.network.data.model.productdetail.ProductDetailResult
import group.beymen.network.ui.theme.PriceRedColor

@Composable
fun ExpandableCardContent(product: ProductDetailResult) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 250.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            item {
                Text(
                    text = product.Brand,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            item {
                Text(
                    text = product.DisplayName,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            item {
                Text(
                    text = stringResource(id = R.string.label_price, product.LabelPrice),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    ),
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }

            item {
                product.ProductPromotion?.let {
                    Row(
                        modifier = Modifier
                            .padding(4.dp)
                            .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                            .padding(horizontal = 4.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = it.CampaignTitle,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 14.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,

                                ),
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = stringResource(id = R.string.promoted_price, it.PromotedPrice),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 14.sp,
                                color = PriceRedColor,
                                fontWeight = FontWeight.ExtraBold
                            ),
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }
            }

            item{Spacer(modifier = Modifier.width(4.dp))}

            item {
                product.Colors?.let { colors ->
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.color_selection),
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(colors) { color ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start,
                                    modifier = Modifier.padding(4.dp)
                                ) {
                                    Text(
                                        text = color.ColorName,
                                        style = MaterialTheme.typography.bodySmall,
                                        modifier = Modifier.padding(end = 8.dp)
                                    )

                                    val circleColor = try {
                                        Color(android.graphics.Color.parseColor(color.ColorCode))
                                    } catch (e: IllegalArgumentException) {
                                        Color.Black
                                    }

                                    Canvas(
                                        modifier = Modifier
                                            .size(16.dp)
                                            .border(1.dp, Color.Gray, CircleShape)
                                    ) {
                                        drawCircle(color = circleColor)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            item {
                product.let { product ->
                    var isExpanded by remember { mutableStateOf(false) }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Divider(color = Color.LightGray, thickness = if (isExpanded) 2.dp else 1.dp)

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { isExpanded = !isExpanded }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_shirt),
                                        contentDescription = stringResource(id = R.string.shirt_icon),
                                        tint = Color.Black
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = stringResource(id = R.string.product_details),
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp
                                        )
                                    )
                                }

                                Icon(
                                    painter = if (isExpanded) {
                                        painterResource(id = R.drawable.ic_close_detail)
                                    } else {
                                        painterResource(id = R.drawable.ic_open_detail)
                                    },
                                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                                    tint = Color.Black
                                )
                            }

                            if (isExpanded) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp)
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.ref_no, product.ExternalSystemCode!!),
                                        style = MaterialTheme.typography.bodySmall,
                                        modifier = Modifier.padding(vertical = 4.dp)
                                    )
                                    product.ParsedProductDetails?.let { details ->
                                        details.forEach { detail ->
                                            Text(
                                                text = stringResource(id = R.string.detail_item, detail),
                                                style = MaterialTheme.typography.bodySmall,
                                                modifier = Modifier.padding(vertical = 2.dp)
                                            )
                                        }
                                    }
                                    product.ModelSize?.let {
                                        Text(
                                            text = stringResource(id = R.string.model_size, it),
                                            style = MaterialTheme.typography.bodySmall,
                                            modifier = Modifier.padding(vertical = 4.dp)
                                        )
                                    }
                                    product.SampleSize?.let {
                                        Text(
                                            text = stringResource(id = R.string.sample_size, it),
                                            style = MaterialTheme.typography.bodySmall,
                                            modifier = Modifier.padding(vertical = 4.dp)
                                        )
                                    }
                                }
                            }
                        }

                        Divider(color = Color.LightGray, thickness = if (isExpanded) 2.dp else 1.dp)
                    }
                }
            }

            /*
            // Ürün Kategorileri (Breadcrumbs)
            item {
                Text(
                    text = "Kategoriler",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                product.BreadcrumbModelList?.forEach { breadcrumb ->
                    Text(
                        text = breadcrumb.DisplayName,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }*/
        }
    }
}