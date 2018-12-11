package net.iplace.iplacehelper.database

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson

/**
 * Created by ${DANavarro} on 11/12/2018.
 */
class Converters() {
    companion object {
        @TypeConverter
        fun listToJson(value: ArrayList<Any>?): String {
            return Gson().toJson(value)
        }

        @TypeConverter
        fun jsonToList(json: String): List<String>? {
            return Gson().fromJson(json, Array<String>::class.java).toList()
        }
    }
}