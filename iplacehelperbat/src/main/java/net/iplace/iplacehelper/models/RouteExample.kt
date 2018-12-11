package net.iplace.iplacehelper.models

import net.iplace.iplacehelper.retrofit.HelperRetrofit
import org.json.JSONObject
import java.util.*

/**
 * Created by ${DANavarro} on 07/12/2018.
 */
class RouteExample(
        val idRuta: Int,
        val nombre: String,
        val tratamiento: Int
){

    companion object {

        private fun map(obj: JSONObject): RouteExample? {
            val idRuta = obj.getInt("idRuta")
            val nombre = obj.getString("nombre")
            val tratamiento = obj.getInt("tratamiento")
            return RouteExample(idRuta, nombre, tratamiento)
        }

        fun handlerResult(callback: (ArrayList<RouteExample>?) -> Unit, json: String) {
            if (HelperRetrofit.getResult(json) > 0) {
                val data = JSONObject(json).getJSONObject("data").getJSONArray("rutas")
                val array = ArrayList<RouteExample>()
                for (i in 0..(data.length() - 1)) {
                    val route: JSONObject = data[i] as JSONObject
                    map(route)?.let {
                        array.add(it)
                    }
                }
                callback(array)
            } else {
                callback(null)
            }
        }
    }

}