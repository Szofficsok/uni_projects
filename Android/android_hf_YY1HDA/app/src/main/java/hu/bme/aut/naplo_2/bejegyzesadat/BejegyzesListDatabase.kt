package hu.bme.aut.naplo_2.bejegyzesadat

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BejegyzesItem::class], version = 2)
abstract class BejegyzesListDatabase : RoomDatabase() {
    abstract fun bejegyzesItemDao(): BejegyzesItemDao

    companion object {
        fun getDatabase(applicationContext: Context): BejegyzesListDatabase {
            return Room.databaseBuilder(
                applicationContext,
                BejegyzesListDatabase::class.java,
                "bejegyzesek"
            ).build();
        }
    }
}