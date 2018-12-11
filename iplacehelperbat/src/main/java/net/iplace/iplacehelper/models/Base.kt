package net.iplace.iplacehelper.models


import com.beust.klaxon.Klaxon

/**
 * Created by ${DANavarro} on 11/12/2018.
 */
abstract class Base {
    fun toJson(): String {
        return Klaxon().toJsonString(this)
    }
}