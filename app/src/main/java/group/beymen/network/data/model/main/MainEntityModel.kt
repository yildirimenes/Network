package group.beymen.network.data.model.main

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MainEntityModel(
    @PrimaryKey
    val id: Int,
    val name: String,
)