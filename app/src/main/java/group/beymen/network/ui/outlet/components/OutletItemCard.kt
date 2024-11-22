package group.beymen.network.ui.outlet.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import group.beymen.network.ui.components.NetworkImageComponents

@Composable
fun OutletItemCard(
    imageUrl: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .clickable { onClick() },
    ) {
        NetworkImageComponents(
            url = imageUrl,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
        )
    }
}
