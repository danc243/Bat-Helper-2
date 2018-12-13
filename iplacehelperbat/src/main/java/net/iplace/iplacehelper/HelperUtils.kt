package net.iplace.iplacehelper

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.preference.Preference
import android.preference.PreferenceManager
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.telephony.TelephonyManager
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import net.iplace.iplacehelper.dialogs.ErrorDialog
import net.iplace.iplacehelper.retrofit.HelperRetrofit
import java.lang.reflect.Array.get

/**
 * Created by ${DANavarro} on 10/12/2018.
 */
class HelperUtils {
    companion object {
        private const val sharedPreferencesKey = "iplaceHelperSPK"
        private const val spkVersionCode = "database_version_code_key"

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
                        ErrorDialog.newErrorDialog(context, e.localizedMessage)
                        null
                    }
                }
            }
            return null
        }
    }

    /**
     * Como usar.
     * HelperUtils.SharedPreferenceHelper(Context).getVersionCode2()
     */
    class SharedPreferenceHelper(val context: Context) {
        private val sharedPreferences = context.getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)
        var versionCode: Int
            get() = sharedPreferences.getInt(spkVersionCode, 0)
            set(value) = sharedPreferences.edit().putInt(spkVersionCode, value).apply()

//        fun getVersionCode2(): Int {
//            return sharedPreferences.getInt(spkVersionCode, 0)
//        }
//
//        fun saveVersionCode(versionCode: Int): HelperUtils.SharedPreferenceHelper {
//            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
//            sharedPreferences.edit().putInt(spkVersionCode, versionCode).apply()
//            return this
//        }

    }
}