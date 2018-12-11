package net.iplace.iplacehelper.database.operador

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import net.iplace.iplacehelper.database.operador.Operador.Companion.table_name

/**
 * Created by ${DANavarro} on 11/12/2018.
 */

@Entity(tableName = table_name)
data class Operador(
        @PrimaryKey val id: Int,
        @ColumnInfo(name = "nombre") var nombre: String?,
        @ColumnInfo(name = "celular") var celulares: String?,
        @ColumnInfo(name = "nextel") var nextel: String?,
        @ColumnInfo(name = "licencia") var licencia: String?

) {
    companion object {
        const val table_name = "table_operador"
    }
}
