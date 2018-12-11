package net.iplace.iplacehelper.database.transportista

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by ${DANavarro} on 11/12/2018.
 */

@Entity(tableName = Transportista.table_name)
data class Transportista(
        @PrimaryKey val id: Int,
        @ColumnInfo(name = "nombre") var nombre: String?
) {
    companion object {
        const val table_name = "table_transportista"
    }
}