package group.beymen.network.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import group.beymen.network.data.model.LanguageModel

@Composable
fun LanguagesDropdown(
    modifier: Modifier = Modifier,
    languagesList: List<LanguageModel>,
    currentLanguage: String,
    onCurrentLanguageChange: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(languagesList.first { it.code == currentLanguage }) }

    Box(
        modifier = modifier
            .wrapContentSize(Alignment.TopEnd)
    ) {
        Row(
            modifier = Modifier
                .clickable { expanded = !expanded }
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = selectedItem.flag),
                contentDescription = selectedItem.code,
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            languagesList.forEach { language ->
                DropdownMenuItem(
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = language.flag),
                                contentDescription = language.code,
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = language.name)
                        }
                    },
                    onClick = {
                        selectedItem = language
                        expanded = false
                        onCurrentLanguageChange(language.code)
                    }
                )
            }
        }
    }
}
