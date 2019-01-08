package net.iplace.bat.login.models


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by ${DANavarro} on 11/12/2018.
 */

data class Transportista(
        @SerializedName("id")
        @Expose
        val id: Int,

        @SerializedName("nombre")
        @Expose
        var nombre: String?
)