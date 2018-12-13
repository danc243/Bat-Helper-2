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
import net.iplace.iplacehelper.retrofit.HelperRetrofit

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_login.setOnClickListener {

            if (HelperUtils.validateEditText(arrayOf(et_login_user, et_login_pass))) {
                HelperRetrofit.login(this, et_login_user.text.toString(), et_login_pass.text.toString()) {
                    it?.let {
                        startActivity(AppListActivity.newIntent(applicationContext, it, et_login_pass.text.toString()))
                        finish()
                    }
                }
            } else {
                // Todo campos no válidos, resaltar el error. Creo que ya lo hace Helper.validateEditText
            }
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
