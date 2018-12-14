package net.iplace.iplacehelper.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import net.iplace.iplacehelper.models.Login

/**
 * Created by ${DANavarro} on 13/12/2018.
 */
@Dao
interface LoginDao {
    @Query("SELECT * FROM ${Login.table_name}")
    fun getAll(): List<Login>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(login: Login)

}