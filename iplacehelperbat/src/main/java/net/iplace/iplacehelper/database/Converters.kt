package net.iplace.iplacehelper.database

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import net.iplace.iplacehelper.models.Login
import net.iplace.iplacehelper.models.Operador
import net.iplace.iplacehelper.models.Transportista
import net.iplace.iplacehelper.models.Unidad

/**
 * Created by ${DANavarro} on 14/12/2018.
 */

class Converters {

    @TypeConverter
    fun jsonToTransportista(json: String): ArrayList<Transportista> {
        val type = object : TypeToken<ArrayList<Transportista>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun transportistaToJson(transportista: ArrayList<Transportista>): String {
        val type = object : TypeToken<ArrayList<Transportista>>() {}.type
        return Gson().toJson(transportista, type)
    }


    @TypeConverter
    fun jsonToUnidades(json: String): ArrayList<Unidad> {
        val type = object : TypeToken<ArrayList<Unidad>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun unidadesToJson(unidades: ArrayList<Unidad>): String =
            Gson().toJson(unidades, object : TypeToken<ArrayList<Unidad>>() {}.type)


    @TypeConverter
    fun jsonToOperadores(json: String): ArrayList<Operador> =
            Gson().fromJson(json, object : TypeToken<ArrayList<Operador>>() {}.type)

    @TypeConverter
    fun operadoresToJson(operadores: ArrayList<Operador>): String =
            Gson().toJson(operadores, object : TypeToken<ArrayList<Operador>>() {}.type)

    @TypeConverter
    fun jsonToAplicaciones(json: String): ArrayList<Login.Aplicacion> =
            Gson().fromJson(json, object : TypeToken<ArrayList<Login.Aplicacion>>() {}.type)

    @TypeConverter
    fun aplicacionesToJson(apps: ArrayList<Login.Aplicacion>): String =
            Gson().toJson(apps, object : TypeToken<ArrayList<Login.Aplicacion>>() {}.type)


}