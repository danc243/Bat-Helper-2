package net.iplace.bat.login.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import net.iplace.bat.login.R
import net.iplace.iplacehelper.models.Login


class AppListActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_list)


    }


    override fun onDestroy() {
        super.onDestroy()
    }


    companion object {
        fun newIntent(context: Context, login: Login): Intent {
            //Todo: Modificar esto cuando tengamos que enviar datos reales.
            val intent = Intent(context, AppListActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            return intent
        }
    }
}
