package hu.bme.aut.naplo_2.adat

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [CelItem::class], version = 1)
abstract class CelListDatabase : RoomDatabase() {
    abstract fun celItemDao(): CelItemDao

    companion object {
        fun getDatabase(applicationContext: Context): CelListDatabase {
            return Room.databaseBuilder(
                applicationContext,
                CelListDatabase::class.java,
                "celok"
            ).build();
        }
    }
}