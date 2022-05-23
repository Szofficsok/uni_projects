package hu.bme.aut.naplo_2.adat

import androidx.room.*

@Dao
interface CelItemDao {
    @Query("SELECT * FROM celitem")
    fun getAll(): List<CelItem>

    @Insert
    fun insert(celItems: CelItem): Long

    @Update
    fun update(celItem: CelItem)

    @Delete
    fun deleteItem(celItem: CelItem)
}