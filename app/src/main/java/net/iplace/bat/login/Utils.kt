package net.iplace.bat.login

import android.content.Context
import net.iplace.bat.login.models.Login

/**
 * Created by ${DANavarro} on 08/01/2019.
 */
class Utils {
    private enum class Apps(val id: Int, val packageString: String) {
        ControlAcceso(1910, "net.iplace.bat.controlacceso"),
        InspeccionTransportePrimario(1920, "net.iplace.bat.inspeccion"),
        InspeccionPatrullas(1930, "net.iplace.bat.inspeccionpatrullas"),
        EntradaMateriales(1940, "net.iplace.bat.entradamateriales"),
        InspeccionGPS(1950, "net.iplace.bat.inspecciongps"),
        NO_APP(0, "");

        companion object {
            fun getIdApp(id: Int): Apps {
                for (enum in Apps.values())
                    if (enum.id == id) return enum
                return NO_APP
            }
        }
    }


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


        const val ControlAccesoApp = "net.iplace.bat.controlacceso"
        const val ControlAccesoID = 1910

        const val InspeccionApp = "net.iplace.bat.inspeccion"
        const val InspeccionID = 1920


        const val InspeccionPatrullasApp = "net.iplace.bat.inspeccionpatrullas"
        const val InspeccionPatrullasID = 1930

        const val EntradaMaterialesApp = "net.iplace.bat.entradamateriales"
        const val EntradaMaterialesID = 1940


        const val InspeccionGPSApp = "net.iplace.bat.inspecciongps"
        const val InspeccionGPSID = 1950


        private const val sharedPreferencesKey = "iplaceHelperSPK"
        private const val spkVersionCode = "database_version_code_key"
        private const val spkJsonCatalogos = "database_json_catalogos"


        fun handleGoToApp(app: Login.Aplicacion): String {
//            return when (app.id) {
//                ControlAccesoID -> ControlAccesoApp
//                InspeccionID -> InspeccionApp
//                InspeccionPatrullasID -> InspeccionPatrullasApp
//                EntradaMaterialesID -> EntradaMaterialesApp
//                else -> ""
//            }

            val ap = Apps.getIdApp(app.id)
            return when (ap) {
                Apps.ControlAcceso -> Apps.ControlAcceso.packageString
                Apps.InspeccionTransportePrimario -> Apps.InspeccionTransportePrimario.packageString
                Apps.InspeccionPatrullas -> Apps.InspeccionPatrullas.packageString
                Apps.EntradaMateriales -> Apps.EntradaMateriales.packageString
                Apps.InspeccionGPS -> Apps.InspeccionGPS.packageString
                Apps.NO_APP -> Apps.NO_APP.packageString
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