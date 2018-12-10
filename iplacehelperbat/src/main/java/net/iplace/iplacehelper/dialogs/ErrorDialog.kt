package net.iplace.iplacehelper.dialogs

import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity

/**
 * Created by ${DANavarro} on 10/12/2018.
 */

class ErrorDialog {
    companion object {
        fun newErrorDialog(context: AppCompatActivity, message: String) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Error")
            builder.setMessage(message)
            builder.setPositiveButton("OK") { dialog, wich ->
                dialog.dismiss()
            }
            val alertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.setCanceledOnTouchOutside(false)
            alertDialog.show()
        }
    }
}