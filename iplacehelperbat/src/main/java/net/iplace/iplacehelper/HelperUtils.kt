package net.iplace.iplacehelper

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.telephony.TelephonyManager
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import net.iplace.iplacehelper.dialogs.InfoDialog
import net.iplace.iplacehelper.models.Login
import net.iplace.iplacehelper.retrofit.HelperRetrofit

/**
 * Created by ${DANavarro} on 10/12/2018.
 */
class HelperUtils {
    companion object {

        const val SEND_USER_INTENT = "net.iplace.iplacehelper.user"
        const val SEND_PASSWORD_INTENT = "net.iplace.iplacehelper.pass"
        const val SEND_IMEI_INTENT = "net.iplace.iplacehelper.imei"
        const val SEND_TOKEN_INTENT = "net.iplace.iplacehelper.token"


        const val LoginApp = "net.iplace.bat.login"
        const val LoginAppID = 1900 // No tiene id


        //todo nombres no reales.

        const val ControlAccesoApp = "net.iplace.bat.controlacceso"
        const val ControlAccesoAppID = 1910

        const val InspeccionApp = "net.iplace.bat_ct_pad"
        const val InspeccionID = 1920


        const val InspeccionPatrullasApp = "net.iplace.bat.inspeccionpatrullas"
        const val InspeccionPatrullasID = 1930


        private const val sharedPreferencesKey = "iplaceHelperSPK"
        private const val spkVersionCode = "database_version_code_key"




        fun handleGoToApp(app: Login.Aplicacion): String {
            return when (app.id) {
                ControlAccesoAppID -> ControlAccesoApp
                InspeccionID -> InspeccionApp
                InspeccionPatrullasID -> InspeccionPatrullasApp
                else -> ""
            }
        }


        fun validateEditText(ets: Array<EditText>): Boolean {

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
                            return it
                        }
                    } catch (ex: Exception) {
                        Log.e(HelperRetrofit.error, ex.localizedMessage)
                        null
                    }
                } else {
                    // Todo versión menor a 26
                    return try {
                        val imei = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
                        imei
                    } catch (e: Exception) {
                        InfoDialog.newInfoDialog(context, e.localizedMessage)
                        null
                    }
                }
            }
            return null
        }
    }

    class SharedPreferenceHelper(val context: Context) {
        private val sharedPreferences = context.getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)
        var versionCode: Int
            get() = sharedPreferences.getInt(spkVersionCode, 0)
            set(value) = sharedPreferences.edit().putInt(spkVersionCode, value).apply()
    }
}