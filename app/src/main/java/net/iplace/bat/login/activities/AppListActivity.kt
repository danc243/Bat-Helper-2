package net.iplace.bat.login.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_app_list.*
import net.iplace.bat.login.R
import net.iplace.bat.login.adapters.RVAppListAdapter
import net.iplace.iplacehelper.BaseActivity
import net.iplace.iplacehelper.HelperUtils
import net.iplace.iplacehelper.background.ReqresDemoJob
import net.iplace.iplacehelper.database.HelperDatabase
import net.iplace.iplacehelper.dialogs.InfoDialog
import net.iplace.iplacehelper.dialogs.ProgressDialog
import net.iplace.iplacehelper.models.Catalogos
import net.iplace.iplacehelper.models.Login
import net.iplace.iplacehelper.retrofit.HelperRetrofit


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


//        AsyncRequestDemoJob.schedule()
//        ReqresDemoJob.schedule()

    }


    private fun getCatalog() {
        val progressDialog = ProgressDialog.newProgressDialog(this)
        progressDialog.show()
        HelperRetrofit.getCatalogos(this, progressDialog) {
            it.let { catalogos ->
                if (catalogos != null) {
                    HelperUtils.SharedPreferenceHelper(this).versionCode = catalogos.version
                    HelperDatabase(this).saveCatalog(catalogos) {
                        catalogosGlobal = catalogos
                        progressDialog.dismiss()
                    }
                } else {
                    // Catálogo Actualizado, conseguirlo de la base de detos.
                    HelperDatabase(this).getCatalogs {
                        it.let { catalogos ->
                            if (catalogos != null) {
                                catalogosGlobal = catalogos
                            }
                            progressDialog.dismiss()
                        }
                    }
                }
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
            //            Toast.makeText(this, app.nombre, Toast.LENGTH_SHORT).show()
            goToApp(app)
        }
        rv_app_list.adapter = rv
    }

    private fun goToApp(app: Login.Aplicacion) {
        val packageNameApp = HelperUtils.handleGoToApp(app)
        val intent = packageManager.getLaunchIntentForPackage(packageNameApp)

        if (intent == null) {
            InfoDialog.newInfoDialog(this, "No tiene la aplicación '${app.nombre}' instalada")
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

    companion object {

        const val PUT_EXTRA_LOGIN_APPS = "PUT_EXTRA_LOGIN_APPS"
        fun newIntent(context: Context, login: Login): Intent {
            val intent = Intent(context, AppListActivity::class.java)
            intent.putExtra(PUT_EXTRA_LOGIN_APPS, login.toJson())
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            return intent
        }
    }

}
