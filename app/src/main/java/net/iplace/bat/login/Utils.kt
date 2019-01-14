package net.iplace.bat.login

import android.content.Context
import net.iplace.bat.login.models.Login

/**
 * Created by ${DANavarro} on 08/01/2019.
 */
class Utils {

    companion object {

        const val SEND_USER_INTENT = "net.iplace.iplacehelper.user"
        const val SEND_PASSWORD_INTENT = "net.iplace.iplacehelper.pass"
        const val SEND_IMEI_INTENT = "net.iplace.iplacehelper.imei"
        const val SEND_TOKEN_INTENT = "net.iplace.iplacehelper.token"
        const val SEND_CATALOG_INTENT = "SEND_CATALOGO_INTENT"
        const val SEND_FROMLOGIN = "SEND_FROMLOGIN"
        const val SEND_IDAPP = "SEND_IDAPP"

        const val LoginApp = "net.iplace.bat.login"
        const val LoginAppID = 1900 // No tiene id


        //todo nombres no reales.

        const val ControlAccesoApp = "net.iplace.bat.controlacceso"
        const val ControlAccesoID = 1910

        const val InspeccionApp = "net.iplace.bat.inspeccion"
        const val InspeccionID = 1920


        const val InspeccionPatrullasApp = "net.iplace.bat.inspeccionpatrullas"
        const val InspeccionPatrullasID = 1930

        const val EntradaMaterialesApp = "net.iplace.bat.entradamateriales"
        const val EntradaMaterialesID = 1940


        private const val sharedPreferencesKey = "iplaceHelperSPK"
        private const val spkVersionCode = "database_version_code_key"
        private const val spkJsonCatalogos = "database_json_catalogos"


        fun handleGoToApp(app: Login.Aplicacion): String {
            return when (app.id) {
                ControlAccesoID -> ControlAccesoApp
                InspeccionID -> InspeccionApp
                InspeccionPatrullasID -> InspeccionPatrullasApp
                EntradaMaterialesID -> EntradaMaterialesApp
                else -> ""
            }
        }


    }

    class SharedPreferenceHelper(val context: Context) {
        private val sharedPreferences = context.getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)
        var versionCode: Int
            get() = sharedPreferences.getInt(spkVersionCode, 0)
            set(value) = sharedPreferences.edit().putInt(spkVersionCode, value).apply()


        var jsonCatalogos: String
            get() = sharedPreferences.getString(spkJsonCatalogos, "0")
            set(value) = sharedPreferences.edit().putString(spkJsonCatalogos, value).apply()
    }


}