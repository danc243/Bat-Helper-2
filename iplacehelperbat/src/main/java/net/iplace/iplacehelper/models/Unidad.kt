package net.iplace.iplacehelper.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * Created by ${DANavarro} on 11/12/2018.
 */

data class Unidad(

        val id: Int,

        @SerializedName("clave")
        @Expose
        var clave: String,

        @SerializedName("marca")
        @Expose
        var marca: String,

        @SerializedName("modelo")
        @Expose
        var modelo: String,


        @SerializedName("color")
        @Expose
        var color: String,

        @SerializedName("placas")
        @Expose
        var placas: String,

        @SerializedName("idTransportista")
        @Expose
        var idTransportista: Int
)