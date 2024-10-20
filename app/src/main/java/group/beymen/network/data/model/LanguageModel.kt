package group.beymen.network.data.model

import androidx.annotation.DrawableRes

data class LanguageModel(
    val code: String,
    val name: String,
    @DrawableRes val flag: Int
)
