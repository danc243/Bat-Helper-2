package net.iplace.iplacehelper.retrofit

import android.support.v7.app.AppCompatActivity
import android.util.Log
import net.iplace.iplacehelper.HelperUtils
import net.iplace.iplacehelper.database.Catalogos
import net.iplace.iplacehelper.dialogs.ErrorDialog
import net.iplace.iplacehelper.dialogs.ProgressDialog
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

        val batAPIService by lazy {
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
                ErrorDialog.newErrorDialog(context, "No activó los permisos para obtener el IMEI")
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
                        ErrorDialog.newErrorDialog(context, it.localizedMessage)
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
                                    callback(Login.handleData(it))
                                } else {
                                    ErrorDialog.newErrorDialog(context, getMessage(it))
                                }
                            }
                        }
                    }
                }
            })
        }


        fun getCatalogos(context: AppCompatActivity, callback: (Catalogos?) -> Unit) {

            val user = this@Companion.user ?: return
            val password = this@Companion.pass ?: return
            val imei = this@Companion.imei ?: return
            val vscode = HelperUtils.SharedPreferenceHelper(context).getVersionCode()

            val body = HashMap<String, String>()
            body.set("login", user)
            body.set("password", password)
            body.set("imei", imei)
            body.set("version", vscode.toString())

            val catalogCall = batAPIService.getCatalogos(body)
            /*
            catalogCall.enqueue
             */

            val s = "{\n" +
                    "\t'result': '1',\n" +
                    "\t'message': 'Operación exitosa',\n" +
                    "\t'data': {\n" +
                    "\t\t'version': '1',\n" +
                    "\t\t'transportista':\n" +
                    "\t\t[{\n" +
                    "\t\t\t\t'id': '3',\n" +
                    "\t\t\t\t'nombre': 'Ryder'\n" +
                    "\t\t\t}\n" +
                    "\t\t],\n" +
                    "\t\t'operador': [{\n" +
                    "\t\t\t\t'id': '56456',\n" +
                    "\t\t\t\t'idTransportista': '564564',\n" +
                    "\t\t\t\t'nombre': 'djjskandjksa',\n" +
                    "\t\t\t\t'celular': 'djkdnsajkdnas',\n" +
                    "\t\t\t\t'nextel': 'djasndjksa',\n" +
                    "\t\t\t\t'licencia': 'djasndjkasnjd'\n" +
                    "\t\t\t}\n" +
                    "\t\t],\n" +
                    "\t\t'unidad': [{\n" +
                    "\t\t\t\t'id': '8888',\n" +
                    "\t\t\t\t'idTransportista': '3',\n" +
                    "\t\t\t\t'clave': 'dsadsad',\n" +
                    "\t\t\t\t'marca': 'aaaa',\n" +
                    "\t\t\t\t'modelo': 'qqqqq',\n" +
                    "\t\t\t\t'placas': 'qqqqqqqq',\n" +
                    "\t\t\t\t'color': 'rojito ayy'\n" +
                    "\t\t\t}\n" +
                    "\t\t]\n" +
                    "\t}\n" +
                    "}"

            callback(Catalogos.handleData(s))

        }

    }


}