package hu.bme.aut.naplo_2.kepadat

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "kepitem")
data class KepItem(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "kepek") var kepek: Kepek
) {
    enum class Kepek {
        ETEL, SPORT, ZENE, FILM, ALLAT, BARATOK, TANULAS, CSALAD;
        companion object {
            @JvmStatic
            @TypeConverter
            fun getByOrdinal(ordinal: Int): Kepek? {
                var ret: Kepek? = null
                for (cat in values()) {
                    if (cat.ordinal == ordinal) {
                        ret = cat
                        break
                    }
                }
                return ret
            }

            @JvmStatic
            @TypeConverter
            fun toInt(kepek: Kepek): Int {
                return kepek.ordinal
            }
        }
    }
}