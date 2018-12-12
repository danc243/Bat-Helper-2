package net.iplace.iplacehelper.models

import com.google.gson.Gson

/**
 * Created by ${DANavarro} on 10/12/2018.
 */
class Login(
        val nombreUsuario: String,
        val nombreHub: String,
        val nombrePuerta: String,
        val aplicaciones: ArrayList<Aplicacion>
) : Base() {

    companion object {
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
            val id: Int,
            val nombre: String
    )
}