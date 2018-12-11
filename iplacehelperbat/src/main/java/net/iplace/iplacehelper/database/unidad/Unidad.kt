package net.iplace.iplacehelper.database.unidad

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


/**
 * Created by ${DANavarro} on 11/12/2018.
 */
@Entity(tableName = Unidad.table_name)
data class Unidad(
        @PrimaryKey val id: Int,
        @ColumnInfo(name = "clave") var clave: String?,
        @ColumnInfo(name = "marca") var marca: String?,
        @ColumnInfo(name = "modelo") var modelo: String?,
        @ColumnInfo(name = "color") var color: String?,
        @ColumnInfo(name = "placas") var placas: String?,
        @ColumnInfo(name = "idTransportista") var idTransportista: Int
) {
    companion object {
        const val table_name = "table_unidad"

        fun toObj(json: String): Unidad {
            return Gson().fromJson(json, Unidad::class.java)
        }

        fun toList(json: String): List<Unidad>? {
            val type = object : TypeToken<List<Unidad>>() {}.type
            return Gson().fromJson(json, type)
        }


    }
}