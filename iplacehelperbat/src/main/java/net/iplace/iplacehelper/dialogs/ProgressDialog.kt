package net.iplace.iplacehelper.dialogs

import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import net.iplace.iplacehelper.R

/**
 * Created by ${DANavarro} on 10/12/2018.
 */
class ProgressDialog {
    companion object {
        fun newProgressDialog(context: AppCompatActivity, message: String = "Cargando"): AlertDialog {
            val builder = AlertDialog.Builder(context)
            val dialogView = context.layoutInflater.inflate(R.layout.progress_dialog, null)
            val tv = dialogView.findViewById<TextView>(R.id.tv_progress_dialog)
            tv.text = message
            builder.setView(dialogView)
            builder.setCancelable(false)
            return builder.create()
        }
    }
}