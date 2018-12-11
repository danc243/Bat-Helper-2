package net.iplace.iplacehelper.models

import com.google.gson.Gson
import org.json.JSONObject

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

        fun jsonToObj(json: String): Login? {
            return Gson().fromJson(json, Login::class.java)
        }

        @Throws(Exception::class)
        private fun map(obj: JSONObject): Login? {
            val nombreUsuario = obj.getString("nombreUsuario")
            val nombreHub = obj.getString("nombreHub")
            val nombrePuerta = obj.getString("nombrePuerta")

            val aplicaciones = ArrayList<Aplicacion>()
            val objApps = obj.getJSONArray("aplicaciones")
            for (i in 0..(objApps.length() - 1)) {
                Aplicacion.map(objApps[i] as JSONObject)?.let {
                    aplicaciones.add(it)
                }
            }
            return Login(nombreUsuario, nombreHub, nombrePuerta, aplicaciones)
        }

        fun handleResult(json: String): Login? {
            return try {
                val data = JSONObject(json).getJSONObject("data")
                map(data)
            } catch (ex: Exception) {
                null
            }
        }
    }


    class Aplicacion(
            val id: Int,
            val nombre: String
    ) {
        companion object {
            @Throws(Exception::class)
            fun map(obj: JSONObject): Aplicacion? {
                val id = obj.getInt("id")
                val nombre = obj.getString("nombre")
                return Aplicacion(id, nombre)
            }

        }
    }
}