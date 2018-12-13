package net.iplace.iplacehelper.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import net.iplace.iplacehelper.models.Transportista

/**
 * Created by ${DANavarro} on 11/12/2018.
 */
@Dao
interface TransportistaDao {

    @Query("SELECT * FROM ${Transportista.table_name}")
    fun getAll(): List<Transportista>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(tranportistas: List<Transportista>)

}