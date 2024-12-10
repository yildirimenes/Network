package group.beymen.network.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import group.beymen.network.data.model.outletpage.OutletPageEntity

@Dao
interface OutletPageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(outletPageEntity: OutletPageEntity)

    @Query("SELECT * FROM outlet_page WHERE id = :id LIMIT 1")
    suspend fun getOutletPage(id: String): OutletPageEntity?

    @Query("DELETE FROM outlet_page")
    suspend fun clear()
}
