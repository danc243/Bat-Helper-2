package net.iplace.bat.login.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import net.iplace.iplacehelper.HelperUtils

/**
 * Created by ${DANavarro} on 12/12/2018.
 */
@Entity(tableName = Catalogos.table_name)
class Catalogos(
        @PrimaryKey
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
) : Base() {

    companion object {
        const val table_name = "table_catalogos"
        const val CATALOG_NOT_UPDATED = 1
        const val CATALOG_UPDATED = 2
        fun handleData(json: String): Catalogos? {
            HelperUtils.getResult(json).let {
                accessData(json)?.let { data ->
                    return Gson().fromJson(data.toString(), Catalogos::class.java)
                }
            }
            return null
        }
    }
}