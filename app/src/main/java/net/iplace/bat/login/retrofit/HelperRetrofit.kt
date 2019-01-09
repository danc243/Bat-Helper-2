package net.iplace.bat.login.retrofit

import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import net.iplace.bat.login.Utils
import net.iplace.iplacehelper.HelperUtils
import net.iplace.iplacehelper.HelperUtils.Companion.getMessage
import net.iplace.iplacehelper.HelperUtils.Companion.getResult
import net.iplace.bat.login.database.HelperDatabase
import net.iplace.iplacehelper.dialogs.InfoDialog
import net.iplace.iplacehelper.dialogs.ProgressDialog
import net.iplace.bat.login.models.Catalogos
import net.iplace.bat.login.models.Login
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by ${DANavarro} on 08/01/2019.
 */
class HelperRetrofit {

    companion object {

        val error = "Error"

        var user: String? = null
        var pass: String? = null
        var imei: String? = null
        var token: String? = null

        private val batAPIService by lazy {
            BatAPIService.create()
        }


        fun login(context: AppCompatActivity, user: String, password: String, callback: (Login?) -> Unit) {
            val imei = HelperUtils.getIMEI(context)
            if (imei == null) {
                InfoDialog.newInfoDialog(context, "No activó los permisos para obtener el IMEI")
                return
            }
            Log.d("IMEI", imei)
            val progressDialog = ProgressDialog.newProgressDialog(context)
            progressDialog.show()
            val body = HashMap<String, String>()
            body["login"] = user
            body["password"] = password
            //TODO pasar el imei
            body["imei"] = "123"
            body["token"] = "30000"


            val loginCall = batAPIService.login(body)
            loginCall.enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>?, t: Throwable?) {
                    t?.let {
                        Log.e(error, it.localizedMessage)
                        progressDialog.dismiss()
                        localLogin(context, user, password, callback)
                    }
                }

                override fun onResponse(call: Call<String>?, response: Response<String>?) {
                    response?.let {
                        progressDialog.dismiss()
                        if (it.isSuccessful) {
                            it.body()?.let {
                                if (HelperUtils.getResult(it) > 0) {
                                    this@Companion.user = body["login"]
                                    this@Companion.imei = body["imei"]
                                    this@Companion.pass = body["password"]
                                    this@Companion.token = body["token"]
                                    Login.handleData(it)?.let { login ->
                                        login.username = this@Companion.user
                                        login.password = this@Companion.pass
                                        HelperDatabase(context).saveUser(login)
                                        callback(login)
                                    }
                                } else {
                                    InfoDialog.newInfoDialog(context, HelperUtils.getMessage(it))
                                }
                            }
                        }
                    }
                }
            })
        }


        private fun localLogin(context: AppCompatActivity, username: String, password: String, callback: (Login?) -> Unit) {
            HelperDatabase(context).getUsers { users ->
                for (user in users) {
                    if (user.username != null && user.password != null) {
                        if (user.username == username && user.password == password) {
                            this@Companion.user = user.username
                            this@Companion.imei = HelperUtils.getIMEI(context)
                            this@Companion.pass = user.password
                            this@Companion.token = "0000"
                            context.runOnUiThread {
                                val builder = AlertDialog.Builder(context)
                                builder.setTitle("Sin internet")
                                builder.setMessage("Accediendo sin conexión, puede que los catálogos estén desactualizados.")
                                builder.setPositiveButton("OK") { _, _ ->
                                    callback(user)
                                }
                                builder.setNegativeButton("Cancelar") { dialog, wich ->
                                    dialog.dismiss()
                                }
                                val alertDialog = builder.create()
                                alertDialog.setCancelable(false)
                                alertDialog.setCanceledOnTouchOutside(false)
                                alertDialog.show()
                            }
                            return@getUsers
                        }
                    }
                }
                context.runOnUiThread {
                    InfoDialog.infoDialog(context, "No se encuentra el usuario en la base de datos local").show()
                }
            }
        }


        private fun getLocalCatalog(context: AppCompatActivity, callback: (Catalogos?) -> Unit) {
            HelperDatabase(context).getCatalogs { catalogos ->
                callback(catalogos)
            }
        }

        /**
         * @param completation(result)
         * 0 = sin internet, consiguiendo catalogo local
         * 1 = trajo información del servidor correctamente
         * 2 = catalogo actualizado, consiguió información local
         * <0 = error
         */
        fun getCatalogos(context: AppCompatActivity, completation: (onFinished: Catalogos?, onError: String?, result: Int) -> Unit) {
            val user = this@Companion.user ?: return
            val password = this@Companion.pass ?: return
            val imei = this@Companion.imei ?: return
            val vscode = Utils.SharedPreferenceHelper(context).versionCode

            val body = HashMap<String, String>()
            body.set("login", user)
            body.set("password", password)
            body.set("imei", imei)
            body.set("version", vscode.toString())


            val call = batAPIService.getCatalogos(body)
            call.enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    getLocalCatalog(context) { catalogo ->
                        completation(catalogo, null, 0)
                    }
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {

                    response.body()?.let { body ->
                        if (!response.isSuccessful) completation(null, getMessage(body), -1)

                        when (getResult(body)) {
                            Catalogos.CATALOG_NOT_UPDATED -> {
                                val helper = HelperDatabase(context)
                                val catalogos = Catalogos.handleData(body)
                                if (catalogos != null) {
                                    completation(catalogos, null, 1)
                                    helper.deleteAllCatalog {
                                        helper.saveCatalog(catalogos){

                                        }
                                    }
                                }

                            }
                            Catalogos.CATALOG_UPDATED -> {
                                getLocalCatalog(context) {
                                    completation(it, null, 2)
                                }
                            }
                            else -> {
                            }
                        }
                    }

                }
            })


        }


        fun asyncRequestDemo(completation: (onFinished: Catalogos?, onError: String?) -> Unit) {

            val body = HashMap<String, String>()
            body.set("login", "adm")
            body.set("password", "dsad")
            body.set("imei", "dasdas")
            body.set("version", "0")

            val call = batAPIService.getCatalogos(body)

            call.enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>?, t: Throwable?) {
                    completation(null, t?.localizedMessage)
                }

                override fun onResponse(call: Call<String>?, response: Response<String>?) {
                    response?.let {
                        if (it.isSuccessful) {
                            response.body()?.let { body ->
                                val res = getResult(body)

                                if (res > 0) {
//                                    completation(Catalogos.handleData(body), null))
                                    completation(Catalogos.handleData(body), null)
                                } else {
                                    completation(null, getMessage(body))
                                }

                            }
                        }
                    }
                }
            })


        }


    }


}