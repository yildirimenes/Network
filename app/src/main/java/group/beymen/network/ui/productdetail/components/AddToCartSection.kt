package group.beymen.network.ui.productdetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import group.beymen.network.R
import group.beymen.network.data.model.productdetail.ProductDetailResult

@Composable
fun AddToCartSection(
    product: ProductDetailResult,
    selectedSize: String?,
    onSizeSelectClick: () -> Unit,
    onAddToCartClick: (String?) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            product.Sizes?.let { sizes ->
                if (sizes.isEmpty()) {
                    Button(
                        onClick = { onAddToCartClick(null) },
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
                } else if (sizes.size == 1) {
                    Button(
                        onClick = { onAddToCartClick(sizes.first().ValueText) },
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
                            onClick = { onSizeSelectClick() },
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
                            onClick = { onAddToCartClick(selectedSize) },
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
            } ?: run {
                Button(
                    onClick = { onAddToCartClick(null) },
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
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
