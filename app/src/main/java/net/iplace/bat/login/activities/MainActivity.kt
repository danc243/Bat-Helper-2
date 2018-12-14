package net.iplace.bat.login.activities


import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import net.iplace.bat.login.R
import net.iplace.iplacehelper.HelperPermissions
import net.iplace.iplacehelper.HelperUtils
import net.iplace.iplacehelper.database.HelperDatabase
import net.iplace.iplacehelper.models.Login
import net.iplace.iplacehelper.retrofit.HelperRetrofit
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_login.setOnClickListener {

            if (HelperUtils.validateEditText(arrayOf(et_login_user, et_login_pass))) {
                HelperRetrofit.login(this, et_login_user.text.toString(), et_login_pass.text.toString()) {
                    it?.let { login ->
                        startActivity(AppListActivity.newIntent(applicationContext, login, et_login_pass.text.toString()))
                        finish()
                    }
                }
            }

/*
//            val s = "{\n" +
//                    "\t'result': '1',\n" +
//                    "\t'message': 'Operación exitosa',\n" +
//                    "\t'data': {\n" +
//                    "\t\t'nombreUsuario': 'Administrador general',\n" +
//                    "\t\t'nombreHub': 'Hub Mty',\n" +
//                    "\t\t'nombrePuerta': 'Arteaga',\n" +
//                    "\t\t'aplicaciones': [{\n" +
//                    "\t\t\t\t'id': '1910',\n" +
//                    "\t\t\t\t'nombre': 'Control de acceso'\n" +
//                    "\t\t\t}, {\n" +
//                    "\t\t\t\t'id': '1920',\n" +
//                    "\t\t\t\t'nombre': 'Inspección'\n" +
//                    "\t\t\t}, {\n" +
//                    "\t\t\t\t'id': '1930',\n" +
//                    "\t\t\t\t'nombre': 'Inspección de patrullas'\n" +
//                    "\t\t\t}\n" +
//                    "\t\t]\n" +
//                    "\t}\n" +
//                    "}\n"
//
//            Login.handleData(s)?.let { login ->
//                login.username = "algo"
//            }
*/
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == HelperPermissions.READ_PHONE_STATE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            btn_login.performClick()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.let {
            outState.putString("login", et_login_user.text.toString())
            outState.putString("pass", et_login_pass.text.toString())
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState?.let {
            et_login_user.setText(it.getString("login", ""))
            et_login_pass.setText(it.getString("pass", ""))
        }
    }


    companion object {
        fun newInstance(context: Context): Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            return intent
        }
    }


}
