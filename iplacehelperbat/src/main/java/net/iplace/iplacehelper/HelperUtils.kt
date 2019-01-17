package net.iplace.iplacehelper

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.telephony.TelephonyManager
import android.util.Base64
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import me.echodev.resizer.Resizer
import net.iplace.iplacehelper.dialogs.InfoDialog
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.HashMap


/**
 * Created by ${DANavarro} on 10/12/2018.
 */
class HelperUtils {


    companion object {

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
                        Log.e("Error", ex.localizedMessage)
                        null
                    }
                } else {
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


        fun createFirmaBase64(
            firma: Firma,
            image: Bitmap
        ): HashMap<String, String> {
            val body = HashMap<String, String>()
            body["nombre"] = firma.nombre
            body["tipo"] = firma.code
            val byteArrayOutputStream = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.WEBP, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            var img = Base64.encodeToString(byteArray, Base64.DEFAULT)
            img = deleteNewLine(img)
            body["raw"] = img
            return body
        }


        fun createPhotoBase64(context: Context, photo: File): HashMap<String, String> {
            val body = HashMap<String, String>()
            val imgShort = Resizer(context)
                .setQuality(50)
                .setOutputFormat("JPEG")
                .setSourceImage(photo)
                .resizedFile
            var img = Base64.encodeToString(imgShort.readBytes(), Base64.DEFAULT)
            img = deleteNewLine(img)
            body["raw"] = img
            return body
        }


        private fun deleteNewLine(s: String): String {
            return s.replace("\n".toRegex(), "")
        }


        enum class Firma(val code: String, val nombre: String) {
            FIRMA_OPERADOR("FO", "firma_operador"),
            FIRMA_CUSTODIO("FC", "firma_custodio")
        }


    }


}