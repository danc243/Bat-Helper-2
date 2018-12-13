package net.iplace.iplacehelper

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.content.PermissionChecker
import android.support.v7.app.AppCompatActivity
import net.iplace.iplacehelper.dialogs.ErrorDialog

/**
 * Created by ${DANavarro} on 10/12/2018.
 */
class HelperPermissions {
    companion object {
        const val READ_PHONE_STATE_PERMISSION = 5482

        fun getIMEIPermissions(context: AppCompatActivity): Boolean {

            //Arriba de versión 23 (Marshmallow, pide permisos en tiempo de ejeción)
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                if (context.checkSelfPermission(android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                    return true
                } else {
                    //No tiene permisos, pedirlos.
                    if (context.shouldShowRequestPermissionRationale(android.Manifest.permission.READ_PHONE_STATE)) {
                        AlertDialog.Builder(context)
                                .setTitle("Se necesitan permisos")
                                .setMessage("Se necesitan estos permisos para obtener el IMEI para poder continuar con la aplicación")
                                .setPositiveButton("Ok") { dialog, which ->
                                    context.requestPermissions(arrayOf(android.Manifest.permission.READ_PHONE_STATE), READ_PHONE_STATE_PERMISSION)
                                }
                                .setNegativeButton("Cancelar") { dialog, which ->
                                    dialog.dismiss()
                                }
                                .create().show()
                    } else {
                        context.requestPermissions(arrayOf(android.Manifest.permission.READ_PHONE_STATE), READ_PHONE_STATE_PERMISSION)
                    }
                }

            } else {
                val permission = PermissionChecker.checkSelfPermission(context, android.Manifest.permission.READ_PHONE_STATE)
                if (permission == PackageManager.PERMISSION_GRANTED) {
                    return true
                } else {
                    ErrorDialog.newErrorDialog(context, "Versión de Android menor a API 23 (Android 6.0 Marshmallow), dar permisos manualmente desde configuración")
                }
            }
            return false
        }

    }
}