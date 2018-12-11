package net.iplace.iplacehelper.database.operador

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

/**
 * Created by ${DANavarro} on 11/12/2018.
 */
@Dao
interface OperadorDao {
    @Query("SELECT * FROM ${Operador.table_name}")
    fun getAll(): ArrayList<Operador>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg operadores: Operador)
}