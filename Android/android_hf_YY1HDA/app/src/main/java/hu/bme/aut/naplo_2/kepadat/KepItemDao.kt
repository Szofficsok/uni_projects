package hu.bme.aut.naplo_2.kepadat

import androidx.room.*

@Dao
interface KepItemDao {
    @Query("SELECT * FROM kepitem")
    fun getAll(): List<KepItem>

    @Insert
    fun insert(kepItems: KepItem): Long

    @Update
    fun update(kepItem: KepItem)

    @Delete
    fun deleteItem(kepItem: KepItem)
}