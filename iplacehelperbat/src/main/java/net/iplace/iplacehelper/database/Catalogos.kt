package net.iplace.iplacehelper.database

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import net.iplace.iplacehelper.models.Base
import net.iplace.iplacehelper.models.Operador
import net.iplace.iplacehelper.models.Transportista
import net.iplace.iplacehelper.models.Unidad

/**
 * Created by ${DANavarro} on 12/12/2018.
 */
class Catalogos(
        val version: Int,
        @SerializedName("transportista") val transportistas: ArrayList<Transportista>,
        @SerializedName("operador") val operadores: ArrayList<Operador>,
        @SerializedName("unidad") val unidades: ArrayList<Unidad>
) : Base() {

    companion object {
        const val CATALOG_UPDATED = 2

        fun handleData(json: String): Catalogos? {
            accessData(json)?.let { data ->
                return Gson().fromJson(data.toString(), Catalogos::class.java)
            }
            return null
        }
    }


}