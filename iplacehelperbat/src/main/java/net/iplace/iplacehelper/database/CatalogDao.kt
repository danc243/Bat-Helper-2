package net.iplace.iplacehelper.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import net.iplace.iplacehelper.models.Catalogos


/**
 * Created by ${DANavarro} on 14/12/2018.
 */
@Dao
interface CatalogDao {
    @Query("SELECT * FROM ${Catalogos.table_name}")
    fun getAll(): List<Catalogos>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(catalogos: Catalogos)

    @Query("DELETE FROM ${Catalogos.table_name}")
    fun deleteAll()

}