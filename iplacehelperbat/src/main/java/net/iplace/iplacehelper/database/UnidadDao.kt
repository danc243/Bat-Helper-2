package net.iplace.iplacehelper.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import net.iplace.iplacehelper.models.Unidad

/**
 * Created by ${DANavarro} on 11/12/2018.
 */
@Dao
interface UnidadDao {
    @Query("SELECT * FROM ${Unidad.table_name}")
    fun getAll(): List<Unidad>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(unidades: List<Unidad>)
}