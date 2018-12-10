package net.iplace.iplacehelper

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import io.reactivex.disposables.Disposable
import net.iplace.iplacehelper.Retrofit.BatAPIService
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import android.content.Context.TELEPHONY_SERVICE
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.telephony.TelephonyManager
import net.iplace.iplacehelper.dialogs.ErrorDialog
import net.iplace.iplacehelper.models.Login
import retrofit2.Callback


/**
 * Created by ${DANavarro} on 07/12/2018.
 */
class IplaceHelper {

    companion object {
        val error = "Error"

        val PERMISSION_READ_PHONE_STATE = "PERMISSION_READ_PHONE_STATE"


        val batAPIService by lazy {
            BatAPIService.create()
        }

        var disposable: Disposable? = null

        fun READ_PHONE_STATE_PERMISSIONS(context: AppCompatActivity) {
            val permission = context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE)


        }

        fun getPermissionsRequests(context: Context) {

        }

        @SuppressLint("MissingPermission")
        @RequiresApi(Build.VERSION_CODES.O)
        fun getIMEI(context: AppCompatActivity): String? {
            try {
                //Permissions. Obtener persmisos desde afuera. Poner esta función cuando ya tenga persmisos.
                val tm = context.getSystemService(TELEPHONY_SERVICE) as TelephonyManager
                tm.getImei(0)?.let {
                    Log.d("IMEI", it)

                }
            } catch (ex: Exception) {
                Log.e(error, ex.localizedMessage)
                return null
            }
            return null
        }


        fun validateEditText() {

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

        fun login(callback: (Login?) -> Unit, context: AppCompatActivity, user: String, password: String) {
            val body = HashMap<String, String>()
            body.set("login", "adm")
            body.set("password", "321")
            body.set("imei", "123")
            body.set("token", "30000")
            val loginCall = batAPIService.login(body)
            loginCall.enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>?, t: Throwable?) {
                    t?.let {
                        Log.e(error, it.localizedMessage)
                        ErrorDialog.newErrorDialog(context, it.localizedMessage)
                    }
                }

                override fun onResponse(call: Call<String>?, response: Response<String>?) {
                    response?.let {
                        if (it.isSuccessful) {
                            it.body()?.let {
                                if (getResult(it) > 0) {
                                    callback(Login.handleResult(it))
                                } else {
                                    ErrorDialog.newErrorDialog(context, getMessage(it))
                                }
                            }
                        }
                    }
                }
            })


        }


    }


}