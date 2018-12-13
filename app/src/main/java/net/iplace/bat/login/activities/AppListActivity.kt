package net.iplace.bat.login.activities

import android.arch.persistence.room.Room
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_app_list.*
import net.iplace.bat.login.R
import net.iplace.bat.login.adapters.RVAppListAdapter
import net.iplace.iplacehelper.HelperUtils
import net.iplace.iplacehelper.database.AppDatabase
import net.iplace.iplacehelper.database.Catalogos
import net.iplace.iplacehelper.dialogs.ProgressDialog
import net.iplace.iplacehelper.models.Login
import net.iplace.iplacehelper.retrofit.HelperRetrofit
import org.jetbrains.anko.doAsync


class AppListActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_list)

        try {
            intent.getStringExtra(PUT_EXTRA_LOGIN_APPS)?.let {
                Login.jsonToObj(it)?.let { login ->
                    init(login)
                    getCatalog()

                }
            }
        } catch (e: Exception) {
            MainActivity.newInstance(this)
            finish()
        }
    }


    private fun getCatalog() {
        HelperRetrofit.getCatalogos(this) {
            it?.let { catalogos ->
                //Guardar catalogos a la base de datos usando room c:
                saveCatalogs(catalogos)

            }
        }
    }

    private fun init(login: Login) {
        setTextViews(login)
        initRV(login.aplicaciones)
    }

    private fun setTextViews(login: Login) {
        tv_app_list_name.text = login.nombreUsuario
        tv_app_list_location.text = login.nombrePuerta
    }

    private fun initRV(apps: ArrayList<Login.Aplicacion>) {
        rv_app_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val rv = RVAppListAdapter(this)
        rv.setElements(apps)
        rv.onItemClick = { app ->
            Toast.makeText(this, app.nombre, Toast.LENGTH_SHORT).show()
        }
        rv_app_list.adapter = rv
    }

    private fun saveCatalogs(catalogos: Catalogos) {
        val db = Room.databaseBuilder(this, AppDatabase::class.java, AppDatabase.db_name).build()
        val dialog = ProgressDialog.newProgressDialog(this@AppListActivity, "Guardando catálogos")
        dialog.show()

        doAsync {

            db.transportistaDao().insertAll(catalogos.transportistas)
            db.operadorDao().insertAll(catalogos.operadores)
            db.unidadDao().insertAll(catalogos.unidades)
            HelperUtils.SharedPreferenceHelper(this@AppListActivity).versionCode = catalogos.version

            dialog.dismiss()
        }


    }

    companion object {

        const val PUT_EXTRA_LOGIN_APPS = "PUT_EXTRA_LOGIN_APPS"
        const val PUT_EXTRA_LOGIN_APPS_PASSWORD = "PUT_EXTRA_LOGIN_APPS_PASSWORD"

        fun newIntent(context: Context, login: Login, password: String): Intent {
            val intent = Intent(context, AppListActivity::class.java)
            intent.putExtra(PUT_EXTRA_LOGIN_APPS_PASSWORD, password)
            intent.putExtra(PUT_EXTRA_LOGIN_APPS, login.toJson())
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            return intent
        }
    }
}
