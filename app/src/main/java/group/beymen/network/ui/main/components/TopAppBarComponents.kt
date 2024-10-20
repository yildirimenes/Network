package group.beymen.network.ui.main.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import group.beymen.network.R
import group.beymen.network.data.model.LanguageModel
import group.beymen.network.ui.components.LanguagesDropdown

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarComponents(
    currentLanguage: String,
    onLanguageChange: (String) -> Unit
) {
    val allLanguages = listOf(
        LanguageModel("en", "English", R.drawable.lang_en),
        LanguageModel("tr", "Turkish", R.drawable.lang_tr),
    )

    Column {
        TopAppBar(
            title = { Text(text = stringResource(id = R.string.app_name)) },
            actions = {
                LanguagesDropdown(
                    languagesList = allLanguages,
                    currentLanguage = currentLanguage,
                    onCurrentLanguageChange = onLanguageChange
                )
            }
        )
        HorizontalDivider()
    }
}
