package net.iplace.bat.login.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * Created by ${DANavarro} on 11/12/2018.
 */

data class Unidad(


        @SerializedName("id")
        @Expose
        val id: Int,

        @SerializedName("idTransportista")
        @Expose
        var idTransportista: Int,

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
        var placas: String


)