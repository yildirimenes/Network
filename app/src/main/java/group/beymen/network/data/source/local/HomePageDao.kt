package group.beymen.network.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import group.beymen.network.ui.homepage.HomePageEntity

@Dao
interface HomePageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(homePageEntity: HomePageEntity)

    @Query("SELECT * FROM home_page LIMIT 1")
    suspend fun getHomePage(): HomePageEntity?

    @Query("DELETE FROM home_page")
    suspend fun clear()
}
