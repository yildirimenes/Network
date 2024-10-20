package group.beymen.network.ui.main.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            title = {
                Text(
                    text = stringResource(id = R.string.app_name),
                    fontWeight = FontWeight.W400,
                    fontSize = 28.sp
                )
            },
            actions = {
                LanguagesDropdown(
                    languagesList = allLanguages,
                    currentLanguage = currentLanguage,
                    onCurrentLanguageChange = onLanguageChange
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .shadow(4.dp),
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Color.White
            ),
        )
        HorizontalDivider()
    }
}
