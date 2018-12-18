package net.iplace.iplacehelper.database

import android.support.v7.app.AppCompatActivity
import net.iplace.iplacehelper.models.Catalogos
import net.iplace.iplacehelper.models.Login
import org.jetbrains.anko.doAsync

/**
 * Created by ${DANavarro} on 14/12/2018.
 */
class HelperDatabase(private val context: AppCompatActivity) {
    private val db = AppDatabase.newInstance(context)

    fun deleteAllCatalog(onFinished: () -> Unit) {
        context.doAsync {
            db.catalogDao().deleteAll()
            db.close()
            onFinished()
        }
    }


    fun saveCatalog(catalogos: Catalogos, onFinished: () -> Unit) {

        context.doAsync {
            db.catalogDao().insert(catalogos)
            db.close()
            onFinished()
        }
    }


    fun getCatalogs(callback: (Catalogos?) -> Unit) {
        //Sólo puede haber una columna de catálogos.

        context.doAsync {
            val catalogos = db.catalogDao().getAll()
            if (catalogos.isEmpty()) {
                callback(null)
                return@doAsync
            }
            if (catalogos.size == 1) {
                callback(catalogos.first())
                return@doAsync
            }
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