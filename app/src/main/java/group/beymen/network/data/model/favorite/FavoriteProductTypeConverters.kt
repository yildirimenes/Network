package group.beymen.network.data.model.favorite

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FavoriteProductTypeConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromImageUrlList(imageUrls: List<String>): String {
        return gson.toJson(imageUrls)
    }

    @TypeConverter
    fun toImageUrlList(imageUrlsJson: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(imageUrlsJson, type)
    }
}
