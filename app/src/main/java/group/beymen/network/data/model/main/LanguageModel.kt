package group.beymen.network.data.model.main

import androidx.annotation.DrawableRes

data class LanguageModel(
    val code: String,
    val name: String,
    @DrawableRes val flag: Int
)
