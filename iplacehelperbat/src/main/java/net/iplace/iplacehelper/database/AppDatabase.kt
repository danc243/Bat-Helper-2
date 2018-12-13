package net.iplace.iplacehelper.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import net.iplace.iplacehelper.HelperUtils
import net.iplace.iplacehelper.models.Operador
import net.iplace.iplacehelper.models.Transportista
import net.iplace.iplacehelper.models.Unidad

/**
 * Created by ${DANavarro} on 11/12/2018.
 */

/**
 * version siempre es 1, use esto para el manejo dinámico.
 * [HelperUtils.SharedPreferenceHelper]
 */
@Database(entities = [
    (Unidad::class),
    (Operador::class),
    (Transportista::class)
], version = 1)

abstract class AppDatabase : RoomDatabase() {
    abstract fun unidadDao(): UnidadDao
    abstract fun operadorDao(): OperadorDao
    abstract fun transportistaDao(): TransportistaDao


    companion object {
        const val db_name = "IplaceHelperDatabase"


    }
}






