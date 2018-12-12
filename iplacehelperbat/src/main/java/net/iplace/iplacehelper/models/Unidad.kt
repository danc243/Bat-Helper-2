package net.iplace.iplacehelper.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken


/**
 * Created by ${DANavarro} on 11/12/2018.
 */
@Entity(tableName = Unidad.table_name)
data class Unidad(
        @SerializedName("id")
        @Expose
        @PrimaryKey val id: Int,

        @SerializedName("clave")
        @Expose
        @ColumnInfo(name = "clave") var clave: String?,

        @SerializedName("marca")
        @Expose
        @ColumnInfo(name = "marca") var marca: String?,

        @SerializedName("modelo")
        @Expose
        @ColumnInfo(name = "modelo") var modelo: String?,


        @SerializedName("color")
        @Expose
        @ColumnInfo(name = "color") var color: String?,

        @SerializedName("placas")
        @Expose
        @ColumnInfo(name = "placas") var placas: String?,

        @SerializedName("idTransportista")
        @Expose
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