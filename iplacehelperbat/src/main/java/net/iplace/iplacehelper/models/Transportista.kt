package net.iplace.iplacehelper.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by ${DANavarro} on 11/12/2018.
 */

@Entity(tableName = Transportista.table_name)
data class Transportista(
        @SerializedName("id")
        @Expose
        @PrimaryKey val id: Int,
        @SerializedName("nombre")
        @Expose
        @ColumnInfo(name = "nombre") var nombre: String?
) {
    companion object {
        const val table_name = "table_transportista"
    }
}