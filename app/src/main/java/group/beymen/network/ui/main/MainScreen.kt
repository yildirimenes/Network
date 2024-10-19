package group.beymen.network.ui.main

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import group.beymen.network.common.UiConfigurationState
import group.beymen.network.ui.navigation.MainNavHost

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController = rememberNavController()) {
    val configuration = remember { mutableStateOf(UiConfigurationState()) }

    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) {
        MainNavHost(navController = navController, configuration = configuration)
    }
}