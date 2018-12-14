package net.iplace.iplacehelper.database

import android.support.v7.app.AppCompatActivity
import net.iplace.iplacehelper.models.Catalogos
import net.iplace.iplacehelper.models.Login
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast

/**
 * Created by ${DANavarro} on 14/12/2018.
 */
class HelperDatabase(private val context: AppCompatActivity) {
    private val db = AppDatabase.newInstance(context)

    fun saveCatalog(catalogos: Catalogos) {

        context.doAsync {
            db.catalogDao().insert(catalogos)
            db.close()
            context.toast("Catálogos guardados!")
        }

    }

    fun saveUser(login: Login) {
        context.doAsync {
            db.loginDao().insert(login)
            db.close()
        }
    }


    fun getUsers(callback: (List<Login>) -> Unit) {
        context.doAsync {
            val a = db.loginDao().getAll()
            db.close()
            callback(a)
        }
    }


}