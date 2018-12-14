package net.iplace.iplacehelper.dialogs

import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import net.iplace.iplacehelper.models.Login

/**
 * Created by ${DANavarro} on 10/12/2018.
 */

class InfoDialog {
    companion object {
        fun newInfoDialog(context: AppCompatActivity, message: String, title: String = "Error") {
            val alertDialog = infoDialog(context, message, title)
            alertDialog.show()
        }

        fun infoDialog(context: AppCompatActivity, message: String, title: String = "Error"): AlertDialog {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(title)
            builder.setMessage(message)
            builder.setPositiveButton("OK") { dialog, wich ->
                dialog.dismiss()
            }
            val alertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.setCanceledOnTouchOutside(false)
            return alertDialog
        }
    }
}