package net.iplace.iplacehelper.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import net.iplace.iplacehelper.HelperUtils
import net.iplace.iplacehelper.models.Catalogos
import net.iplace.iplacehelper.models.Login

/**
 * Created by ${DANavarro} on 11/12/2018.
 */

@TypeConverters(Converters::class)
@Database(entities = [
    (Catalogos::class),
    (Login::class)
], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun loginDao(): LoginDao
    abstract fun catalogDao(): CatalogDao

    companion object {
        const val db_name = "db_bat_helper"
        fun newInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, db_name).build()
        }
    }
}


//@Database(entities = [
////    (Unidad::class),
////    (Operador::class),
////    (Transportista::class),
//    (Login::class)
//], version = 1)
//
//abstract class AppDatabase : RoomDatabase() {
////    abstract fun unidadDao(): UnidadDao
////    abstract fun operadorDao(): OperadorDao
////    abstract fun transportistaDao(): TransportistaDao
//    abstract fun loginDao(): LoginDao
//
//    companion object {
//        const val db_name = "IplaceHelperDatabase"
//
//        fun newInstance(context: Context): AppDatabase {
//            return Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.db_name).build()
//        }
//
//    }
//}
//
//



