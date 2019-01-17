package net.iplace.bat.login.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_app_list.*
import net.iplace.bat.login.R
import net.iplace.bat.login.Utils
import net.iplace.bat.login.adapters.RVAppListAdapter
import net.iplace.bat.login.models.Catalogos
import net.iplace.bat.login.models.Login
import net.iplace.bat.login.retrofit.HelperRetrofit
import net.iplace.iplacehelper.BaseActivity
import net.iplace.iplacehelper.dialogs.InfoDialog


class AppListActivity : BaseActivity(MainActivity::class.java) {

    private lateinit var catalogosGlobal: Catalogos
    private lateinit var login: Login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_list)

        try {
            intent.getStringExtra(PUT_EXTRA_LOGIN_APPS)?.let {
                Login.jsonToObj(it)?.let { login ->
                    this@AppListActivity.login = login
                    init(this@AppListActivity.login)
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

        HelperRetrofit.getCatalogos(this) { catalogos: Catalogos?, onError: String?, result: Int ->
            if (onError != null) InfoDialog.newInfoDialog(this, onError, "Error")
            if (catalogos != null) {
                catalogosGlobal = catalogos
                when (result) {
                    1 -> {

                    }
                    2 -> {

                    }
                    0 -> {
                        val a = AlertDialog.Builder(this)
                                .setTitle("Alerta")
                                .setMessage("Puede que los catálogos estén desactualizado")
                                .setPositiveButton("OK") { dialog, _ ->
                                    dialog.dismiss()
                                }
                                .setNegativeButton("Cancelar") { dialog, _ ->
                                    dialog.dismiss()
                                    this.finish()
                                }
                        val dialog = a.create()
                        dialog.setCancelable(false)
                        dialog.setCanceledOnTouchOutside(false)
                        dialog.show()
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
            goToApp(app)
        }
        rv_app_list.adapter = rv
    }

    private fun goToApp(app: Login.Aplicacion) {

        val packageNameApp = Utils.handleGoToApp(app)
        val intent = packageManager.getLaunchIntentForPackage(packageNameApp)

        if (intent == null) {
            InfoDialog.newInfoDialog(this, "No tiene la aplicación '${app.nombre}' instalada")
            return
        }

        val user = HelperRetrofit.user ?: return
        val pass = HelperRetrofit.pass ?: return
        val imei = HelperRetrofit.imei ?: return
        val token = HelperRetrofit.token ?: return

        intent.putExtra(Utils.SEND_USER_INTENT, user)
        intent.putExtra(Utils.SEND_PASSWORD_INTENT, pass)
        intent.putExtra(Utils.SEND_IMEI_INTENT, imei)
        intent.putExtra(Utils.SEND_TOKEN_INTENT, token)
        intent.putExtra(Utils.SEND_FROMLOGIN, true)
        intent.putExtra(Utils.SEND_IDAPP, app.id)
        val kek = Gson().toJson(catalogosGlobal)
        intent.putExtra(Utils.SEND_CATALOG_INTENT, kek)
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
