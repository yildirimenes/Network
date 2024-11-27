package group.beymen.network.ui.account

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import group.beymen.network.R
import group.beymen.network.common.LanguageChangeHelper
import group.beymen.network.data.model.main.LanguageModel
import group.beymen.network.ui.account.components.SettingsComponents
import group.beymen.network.ui.components.LanguagesDropdown
import group.beymen.network.ui.main.components.BottomBarComponents

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    navController: NavHostController,
    languageChangeHelper: LanguageChangeHelper = LanguageChangeHelper(),
    currentLanguage: String,
    onLanguageChange: (String) -> Unit
) {
    val allLanguages = listOf(
        LanguageModel("en", "English", R.drawable.lang_en),
        LanguageModel("tr", "Turkish", R.drawable.lang_tr),
    )

    val context = LocalContext.current // Obtain the context here
    var isDarkModeEnabled by remember { mutableStateOf(false) }
    var isNetworkModeEnabled by remember { mutableStateOf(false) }
    var isLanguageModeEnabled by remember { mutableStateOf(false) }


    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(id = R.string.account_title),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    },
                )
                HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
            }
        },
        bottomBar = { BottomBarComponents(navController = navController) },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.hello_name),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                SettingsComponents(
                    icon = Icons.Default.Person,
                    text = stringResource(id = R.string.personal_info),
                    onClick = { }
                )
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
                // Light/Dark Mode
                /*
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp, horizontal = 16.dp)
                        .clickable { isDarkModeEnabled = !isDarkModeEnabled },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Build,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = stringResource(id = R.string.dark_mode),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                    Switch(
                        checked = isDarkModeEnabled,
                        onCheckedChange = { isDarkModeEnabled = it }
                    )
                }
                HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
                */
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp, horizontal = 16.dp)
                        .clickable { isNetworkModeEnabled = !isNetworkModeEnabled },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = stringResource(id = R.string.network_mode),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                    Switch(
                        checked = isNetworkModeEnabled,
                        onCheckedChange = { isNetworkModeEnabled = it }
                    )
                }
                HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp, horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Place,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = stringResource(id = R.string.switch_language),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                    Switch(
                        checked = currentLanguage == "en",
                        onCheckedChange = { isChecked ->
                            val newLanguage = if (isChecked) "en" else "tr"
                            onLanguageChange(newLanguage)
                        }
                    )
                }
                HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
                /* Language Dropdown
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp, horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.language_mode),
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxWidth(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    LanguagesDropdown(
                        languagesList = listOf(
                            LanguageModel("en", "English", R.drawable.lang_en),
                            LanguageModel("tr", "Turkish", R.drawable.lang_tr)
                        ),
                        currentLanguage = currentLanguage,
                        onCurrentLanguageChange = { newLanguage ->
                            onLanguageChange(newLanguage)
                            languageChangeHelper.changeLanguage(context, newLanguage)
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
                */

            }
        }
    )
}
