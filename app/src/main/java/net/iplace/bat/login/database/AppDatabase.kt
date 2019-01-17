package net.iplace.bat.login.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import net.iplace.bat.login.models.Catalogos
import net.iplace.bat.login.models.Login

/**
 * Created by ${DANavarro} on 11/12/2018.
 */

@TypeConverters(Converters::class)
@Database(entities = [
    (Catalogos::class),
    (Login::class)
], version = 1, exportSchema = false)
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

