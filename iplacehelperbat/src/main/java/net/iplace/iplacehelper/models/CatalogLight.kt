package net.iplace.iplacehelper.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by ${DANavarro} on 08/01/2019.
 */
data class CatalogLight(
        @SerializedName("version")
        @Expose
        val version: Int,
        @SerializedName("transportista")
        @Expose
        val transportistas: ArrayList<Transportista>,

        @SerializedName("operador")
        @Expose
        val operadores: ArrayList<Operador>,

        @SerializedName("unidad")
        @Expose
        val unidades: ArrayList<Unidad>
)

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

data class Transportista(
        @SerializedName("id")
        @Expose
        val id: Int,

        @SerializedName("nombre")
        @Expose
        var nombre: String?
)

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