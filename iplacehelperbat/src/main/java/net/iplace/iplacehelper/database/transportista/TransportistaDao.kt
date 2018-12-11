package net.iplace.iplacehelper.database.transportista

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

/**
 * Created by ${DANavarro} on 11/12/2018.
 */
@Dao
interface TransportistaDao {

    @Query("SELECT * FROM ${Transportista.table_name}")
    fun getAll(): List<Transportista>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg tranportistas: Transportista)

}