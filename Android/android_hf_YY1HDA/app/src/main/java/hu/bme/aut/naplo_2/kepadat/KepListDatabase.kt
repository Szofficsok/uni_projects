package hu.bme.aut.naplo_2.kepadat

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [KepItem::class], version = 2)
@TypeConverters(value = [KepItem.Kepek::class])
abstract class KepListDatabase : RoomDatabase() {
    abstract fun kepItemDao(): KepItemDao

    companion object {
        fun getDatabase(applicationContext: Context): KepListDatabase {
            return Room.databaseBuilder(
                applicationContext,
                KepListDatabase::class.java,
                "kep-lists"
            ).build();
        }
    }
}