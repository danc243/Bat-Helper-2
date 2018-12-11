package net.iplace.iplacehelper

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity

/**
 * Created by ${DANavarro} on 10/12/2018.
 */
class HelperPermissions {
    companion object {
        const val READ_PHONE_STATE_PERMISSION = 5482

        fun getIMEIPermissions(context: AppCompatActivity): Boolean {
            if (context.checkSelfPermission(android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(context, android.Manifest.permission.READ_PHONE_STATE)) {
                    ActivityCompat.requestPermissions(context, arrayOf(android.Manifest.permission.READ_PHONE_STATE), READ_PHONE_STATE_PERMISSION)
                } else {
                    ActivityCompat.requestPermissions(context, arrayOf(android.Manifest.permission.READ_PHONE_STATE), READ_PHONE_STATE_PERMISSION)
                }
                return false
            }
            return true
        }

    }
}