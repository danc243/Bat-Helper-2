package net.iplace.iplacehelper.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by ${DANavarro} on 11/12/2018.
 */


data class Operador(

        @SerializedName("id")
        @Expose
        val id: Int,

        @SerializedName("idTransportista")
        @Expose
        var idTransportista: Int,


        @SerializedName("nombre")
        @Expose
        var nombre: String,

        @SerializedName("celular")
        @Expose
        var celular: String,

        @SerializedName("nextel")
        @Expose
        var nextel: String,

        @SerializedName("licencia")
        @Expose
        var licencia: String

)
