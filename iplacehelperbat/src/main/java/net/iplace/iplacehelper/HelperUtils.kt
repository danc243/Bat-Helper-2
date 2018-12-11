package net.iplace.iplacehelper

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.telephony.TelephonyManager
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import net.iplace.iplacehelper.dialogs.ErrorDialog
import net.iplace.iplacehelper.retrofit.HelperRetrofit

/**
 * Created by ${DANavarro} on 10/12/2018.
 */
class HelperUtils {
    companion object {

        fun validateEditText(ets: Array<EditText>): Boolean {
            //Si está mal uno de los textos, texto mal ? marca en rojo : genera un dialog
            val bools = arrayListOf<Boolean>()
            for (et in ets) {
                val text = et.text.toString()
                if (text.isEmpty()) {
                    et.inputType = EditorInfo.TYPE_TEXT_FLAG_NO_SUGGESTIONS
                    bools.add(false)
                } else {
                    bools.add(true)
                }
            }
            return !bools.contains(false)
        }

        @SuppressLint("MissingPermission")
        fun getIMEI(context: AppCompatActivity): String? {
            if (HelperPermissions.getIMEIPermissions(context)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    return try {
                        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                        tm.getImei(0)?.let {
                            Log.d("IMEI", it)
                            return it
                        }
                    } catch (ex: Exception) {
                        Log.e(HelperRetrofit.error, ex.localizedMessage)
                        null
                    }
                } else {
                    // Todo versión menor a 26
                    try {
                        val imei = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
                    } catch (e: Exception) {
                        ErrorDialog.newErrorDialog(context, e.localizedMessage)
                        return null
                    }
                }
            } else {
                ActivityCompat.requestPermissions(context, arrayOf(android.Manifest.permission.READ_PHONE_STATE), HelperPermissions.READ_PHONE_STATE_PERMISSION)
            }
            return null
        }
    }
}