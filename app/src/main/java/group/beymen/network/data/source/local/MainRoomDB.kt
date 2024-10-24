package group.beymen.network.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import group.beymen.network.data.model.main.MainEntityModel

@Database(entities = [MainEntityModel::class], version = 1, exportSchema = false)
abstract class MainRoomDB : RoomDatabase() {
    abstract fun mainDao(): MainDao
}