package net.iplace.bat.login.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * Created by ${DANavarro} on 10/12/2018.
 */
@Entity(tableName = Login.table_name)
class Login(


        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        val id: Int,

        @SerializedName("nombreUsuario")
        @Expose
        @ColumnInfo(name = "nombreUsuario")
        val nombreUsuario: String?,

        @SerializedName("nombreHub")
        @Expose
        @ColumnInfo(name = "nombreHub")
        val nombreHub: String?,

        @SerializedName("nombrePuerta")
        @Expose
        @ColumnInfo(name = "nombrePuerta")
        val nombrePuerta: String?,


        @SerializedName("aplicaciones")
        @Expose
        @ColumnInfo(name = "aplicaciones")
        val aplicaciones: ArrayList<Aplicacion>,


        @ColumnInfo(name = "username")
        var username: String?,


        @ColumnInfo(name = "password")
        var password: String?


) : Base() {

    companion object {

        const val table_name = "table_login"

        /**
         * Usar sólo cuando el json no esté encapsulado en "data: {}",
         * O sea, es json puro de la clase [Login]
         *
         */
        fun jsonToObj(json: String): Login? {
            return Gson().fromJson(json, Login::class.java)
        }

        /**
         * Usar sólo cuando el esté encapuslado en "data":{}
         */
        fun handleData(json: String): Login? {
            return try {
                val data = accessData(json)
                return Gson().fromJson(data.toString(), Login::class.java)
            } catch (ex: Exception) {
                null
            }
        }
    }


    data class Aplicacion(
            @SerializedName("id")
            @Expose
            val id: Int,
            @SerializedName("nombre")
            @Expose
            val nombre: String
    )
}
