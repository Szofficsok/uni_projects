package hu.bme.aut.naplo_2.bejegyzesadat

import androidx.room.*

@Dao
interface BejegyzesItemDao {
    @Query("SELECT * FROM bejegyzesitem")
    fun getAll(): List<BejegyzesItem>

    @Insert
    fun insert(bejegyzesItems: BejegyzesItem): Long

    @Update
    fun update(bejegyzesItem: BejegyzesItem)

    @Delete
    fun deleteItem(bejegyzesItem: BejegyzesItem)
}