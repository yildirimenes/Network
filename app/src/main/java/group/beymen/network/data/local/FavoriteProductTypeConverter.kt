package group.beymen.network.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FavoriteProductTypeConverter {
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
