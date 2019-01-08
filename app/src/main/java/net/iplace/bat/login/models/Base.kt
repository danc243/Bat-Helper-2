package net.iplace.bat.login.models


import com.google.gson.Gson
import org.json.JSONObject

/**
 * Created by ${DANavarro} on 11/12/2018.
 */
abstract class Base {
    /**
     * Sólo usar cuando el json NO ESTÉ encapsulado en el data:
     */
    fun toJson(): String {
        return Gson().toJson(this)
    }

    companion object {
        fun accessData(json: String): JSONObject? {
            return JSONObject(json).getJSONObject("data")
        }
    }


}