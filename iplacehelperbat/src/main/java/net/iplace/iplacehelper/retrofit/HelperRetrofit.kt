package net.iplace.iplacehelper.retrofit

import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import net.iplace.iplacehelper.HelperUtils
import net.iplace.iplacehelper.database.HelperDatabase
import net.iplace.iplacehelper.dialogs.InfoDialog
import net.iplace.iplacehelper.dialogs.ProgressDialog
import net.iplace.iplacehelper.models.Catalogos
import net.iplace.iplacehelper.models.Login
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Created by ${DANavarro} on 07/12/2018.
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

        fun getResult(json: String): Int {
            val obj = JSONObject(json)
            val res = obj.getString("result")
            return if (res.isNotEmpty() || res.isNotBlank()) res.toInt() else 0
        }

        fun getMessage(json: String): String {
            val obj = JSONObject(json)
            val message = obj.getString("message")
            return if (message.isNotBlank() || message.isNotEmpty()) message else "No Message"
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
            body.set("login", user)
            body.set("password", password)
            //TODO pasar el imei
            body.set("imei", "123")
            body.set("token", "30000")


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
                                if (getResult(it) > 0) {
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
                                    InfoDialog.newInfoDialog(context, getMessage(it))
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

        /**
         * @param callback
         * Si no es nulo = Actualizaron catálogos
         * Si es nulo = Los catálogos YA estaban actualizados (version es igual a la última)
         * En ambos el usuario procede.
         * Si nunca llega al callback, es porque tiene mal las credenciales (user, pass, imei)
         */
        fun getCatalogos(context: AppCompatActivity, progressDialog: AlertDialog, callback: (Catalogos?) -> Unit) {

            val user = this@Companion.user ?: return
            val password = this@Companion.pass ?: return
            val imei = this@Companion.imei ?: return
            val vscode = HelperUtils.SharedPreferenceHelper(context).versionCode


            val body = HashMap<String, String>()
            body.set("login", user)
            body.set("password", password)
            body.set("imei", imei)
            body.set("version", vscode.toString())

            val catalogCall = batAPIService.getCatalogos(body)
            catalogCall.enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>?, t: Throwable?) {
                    t?.let {
                        getLocalCatalog(context) { catalogos ->
                            progressDialog.dismiss()
                            val a = AlertDialog.Builder(context)
                                    .setTitle("Alerta")
                                    .setMessage("Puede que los catálogos estén desactualizado")
                                    .setPositiveButton("OK") { dialog, _ ->
                                        dialog.dismiss()
                                        callback(catalogos)
                                    }
                                    .setNegativeButton("Cancelar") { dialog, which ->
                                        dialog.dismiss()
                                        context.finish()
                                    }
                            val dialog = a.create()
                            dialog.setCancelable(false)
                            dialog.setCanceledOnTouchOutside(false)
                            dialog.show()
                        }
                    }
                }

                override fun onResponse(call: Call<String>?, response: Response<String>?) {
                    response?.let { response ->
                        if (response.isSuccessful) {
                            response.body()?.let { body ->
                                val res = getResult(body)
                                when {
                                    res == Catalogos.CATALOG_NOT_UPDATED -> {
                                        HelperDatabase(context).deleteAllCatalog {
                                            callback(Catalogos.handleData(body))
                                        }
                                    }
                                    res == Catalogos.CATALOG_UPDATED -> {
//                                        InfoDialog.newInfoDialog(context, getMessage(body))
                                        callback(null)
                                    }
                                    res < 0 -> {

                                    }
                                }
                            }
                        }
                    }

                }
            })
        }


        private fun getLocalCatalog(context: AppCompatActivity, callback: (Catalogos?) -> Unit) {
            HelperDatabase(context).getCatalogs { catalogos ->
                callback(catalogos)
            }
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