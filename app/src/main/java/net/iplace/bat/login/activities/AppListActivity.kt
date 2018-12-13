package net.iplace.bat.login.activities

import android.arch.persistence.room.Room
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_app_list.*
import net.iplace.bat.login.R
import net.iplace.bat.login.adapters.RVAppListAdapter
import net.iplace.iplacehelper.BaseActivity
import net.iplace.iplacehelper.HelperUtils
import net.iplace.iplacehelper.database.AppDatabase
import net.iplace.iplacehelper.database.Catalogos
import net.iplace.iplacehelper.dialogs.ErrorDialog
import net.iplace.iplacehelper.dialogs.ProgressDialog
import net.iplace.iplacehelper.models.Login
import net.iplace.iplacehelper.models.Operador
import net.iplace.iplacehelper.models.Transportista
import net.iplace.iplacehelper.models.Unidad
import net.iplace.iplacehelper.retrofit.HelperRetrofit
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast


class AppListActivity : BaseActivity(MainActivity::class.java) {
    private lateinit var catalogosGlobal: Catalogos

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
            startActivity(MainActivity.newInstance(this))
            finish()
        }
        btn_app_list_update_catalog.setOnClickListener { getCatalog() }


    }


    private fun getCatalog() {
        HelperRetrofit.getCatalogos(this) {
            it.let { catalogos ->
                if (catalogos != null) {
                    saveCatalogs(catalogos)
                } else {
                    toast("El catálogo está actualizado")
                    getCatalogRoom {
                        catalogosGlobal = it
                    }
                }
            }
        }
    }

    private fun getCatalogRoom(callback: (Catalogos) -> Unit) {
        val db = Room.databaseBuilder(this, AppDatabase::class.java, AppDatabase.db_name).build()
        doAsync {
            val version = HelperUtils.SharedPreferenceHelper(this@AppListActivity).versionCode
            val transportistas = db.transportistaDao().getAll()
            val unidades = db.unidadDao().getAll()
            val operadores = db.operadorDao().getAll()

            val transportistasArrayList = ArrayList<Transportista>()
            val unidadessArrayList = ArrayList<Unidad>()
            val operadoresArrayList = ArrayList<Operador>()

            for (trans in transportistas) transportistasArrayList.add(trans)
            for (unid in unidades) unidadessArrayList.add(unid)
            for (op in operadores) operadoresArrayList.add(op)

            val catalogos = Catalogos(version, transportistasArrayList, operadoresArrayList, unidadessArrayList)
            callback(catalogos)
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
            //            Toast.makeText(this, app.nombre, Toast.LENGTH_SHORT).show()
            goToApp(app)
        }
        rv_app_list.adapter = rv
    }

    private fun goToApp(app: Login.Aplicacion) {
        val packageNameApp = HelperUtils.handleGoToApp(app)
        val intent = packageManager.getLaunchIntentForPackage(packageNameApp)

        if (intent == null) {
            ErrorDialog.newErrorDialog(this, "No tiene la aplicación '${app.nombre}' instalada")
            return
        }


        val user = HelperRetrofit.user ?: return
        val pass = HelperRetrofit.pass ?: return
        val imei = HelperRetrofit.imei ?: return
        val token = HelperRetrofit.token ?: return



        intent.putExtra(HelperUtils.SEND_USER_INTENT, user)
        intent.putExtra(HelperUtils.SEND_PASSWORD_INTENT, pass)
        intent.putExtra(HelperUtils.SEND_IMEI_INTENT, imei)
        intent.putExtra(HelperUtils.SEND_TOKEN_INTENT, token)


        startActivity(intent)


    }

    override fun onUserInteraction() {
        super.onUserInteraction()

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
            catalogosGlobal = catalogos
            dialog.dismiss()
            toast("Catálogos actualizados.")
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
