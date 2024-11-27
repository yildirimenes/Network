package group.beymen.network.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import group.beymen.network.common.LanguageChangeHelper
import group.beymen.network.ui.main.MainScreen
import group.beymen.network.ui.main.MainViewModel
import group.beymen.network.ui.theme.MyappTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()
    private val languageChangeHelper by lazy { LanguageChangeHelper() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mainViewModel.loginCheck()
        installSplashScreen().apply {
            setKeepOnScreenCondition{
                mainViewModel.isLoading.value
            }
        }

        setContent {
            val currentLanguageCode = languageChangeHelper.getLanguageCode(applicationContext)
            var currentLanguage by remember { mutableStateOf(currentLanguageCode) }

            MyappTheme {
                MainScreen(
                    currentLanguage = currentLanguage,
                    onLanguageChange = { newLanguage ->
                        currentLanguage = newLanguage
                        languageChangeHelper.changeLanguage(applicationContext, newLanguage)
                    }
                )
            }
        }
    }
}
