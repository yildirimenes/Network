package group.beymen.network.ui.homepage

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "home_page")
data class HomePageEntity(
    @PrimaryKey val id: Int = 1,
    val data: String
)
