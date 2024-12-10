package group.beymen.network.ui.homepage

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import group.beymen.network.R
import group.beymen.network.ui.components.ErrorComponents
import group.beymen.network.ui.components.LoadingBarComponents
import group.beymen.network.ui.main.components.BottomBarComponents
import group.beymen.network.data.util.Resource
import group.beymen.network.ui.components.CustomTopAppBar
import group.beymen.network.ui.homepage.components.HomeContent

@Composable
fun HomePageScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onClickItem: (productId: Int?, categoryId: Int?, webUrl: String?) -> Unit
) {
    val state by homeViewModel.mainPageState.collectAsState()

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = stringResource(id = R.string.app_name),
            )
        },
        bottomBar = { BottomBarComponents(navController = navController) },
        content = { paddingValues ->
            when (state) {
                is Resource.Loading -> {
                    LoadingBarComponents()
                }
                is Resource.Success -> {
                    state.data?.Result?.let { result ->
                        HomeContent(items = result, onClickItem = onClickItem, paddingValues = paddingValues)
                    }
                }
                is Resource.Error -> {
                    val cachedResult = (state as? Resource.Success)?.data?.Result
                    if (cachedResult != null) {
                        HomeContent(items = cachedResult, onClickItem = onClickItem)
                    } else {
                        ErrorComponents(
                            title = stringResource(id = R.string.app_name),
                            onRetry = { homeViewModel.getLoadPage() }
                        )
                    }
                }
            }
        }
    )
}