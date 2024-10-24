package group.beymen.network.ui.outlet

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import group.beymen.network.ui.components.LoadingBarComponents
import group.beymen.network.ui.outlet.components.OutletItemCard

@Composable
fun OutletScreen(navController: NavHostController, viewModel: OutletViewModel = hiltViewModel()) {

    LaunchedEffect(Unit) {
        viewModel.fetchOutletItems("N", "network-app-outlet")
    }

    val outletState = viewModel.outletState

    when {
        outletState.isLoading -> {
            LoadingBarComponents()
        }
        outletState.error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Hata: ${outletState.error}")
            }
        }
        outletState.outletItems != null -> {
            LazyColumn(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize()
                    .padding(top = 100.dp, bottom = 110.dp, start = 8.dp, end = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(outletState.outletItems) { item ->
                    item.ImageUrl?.let { imageUrl ->
                        OutletItemCard(imageUrl, item.Link)
                    }
                }
            }
        }
    }
}

fun openLink(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(intent)
}
