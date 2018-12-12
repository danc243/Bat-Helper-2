package net.iplace.iplacehelper.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import net.iplace.iplacehelper.models.Operador.Companion.table_name

/**
 * Created by ${DANavarro} on 11/12/2018.
 */

@Entity(tableName = table_name)
data class Operador(
        @SerializedName("id")
        @Expose
        @PrimaryKey val id: Int,


        @SerializedName("idTransportista")
        @Expose
        @ColumnInfo(name = "idTransportista") var idTransportista: Int,


        @SerializedName("nombre")
        @Expose
        @ColumnInfo(name = "nombre") var nombre: String?,

        @SerializedName("celular")
        @Expose
        @ColumnInfo(name = "celular") var celular: String?,

        @SerializedName("nextel")
        @Expose
        @ColumnInfo(name = "nextel") var nextel: String?,

        @SerializedName("licencia")
        @Expose
        @ColumnInfo(name = "licencia") var licencia: String?

) {
    companion object {
        const val table_name = "table_operador"
    }
}
