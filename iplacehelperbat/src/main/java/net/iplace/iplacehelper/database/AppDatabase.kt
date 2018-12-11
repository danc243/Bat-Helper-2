package net.iplace.iplacehelper.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import net.iplace.iplacehelper.database.operador.Operador
import net.iplace.iplacehelper.database.operador.OperadorDao
import net.iplace.iplacehelper.database.transportista.Transportista
import net.iplace.iplacehelper.database.unidad.Unidad
import net.iplace.iplacehelper.database.unidad.UnidadDao

/**
 * Created by ${DANavarro} on 11/12/2018.
 */


@Database(entities = [
    (Unidad::class),
    (Operador::class),
    (Transportista::class)
], version = 1/*todo conseguir la versión con sharedPreferences(?)*/)

abstract class AppDatabase : RoomDatabase() {
    abstract fun unidadDao(): UnidadDao
    abstract fun operadorDao(): OperadorDao

    companion object {
        const val db_name = "IplaceHelperDatabase"

    }
}




