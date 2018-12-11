package net.iplace.bat.login.activities


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import net.iplace.bat.login.R
import net.iplace.iplacehelper.HelperUtils
import net.iplace.iplacehelper.retrofit.HelperRetrofit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btn_login.setOnClickListener(View.OnClickListener {

            if (HelperUtils.validateEditText(arrayOf(et_login_user, et_login_pass))) {
                HelperRetrofit.login(this, et_login_user.text.toString(), et_login_pass.text.toString()) {
                    it?.let {
                        startActivity(AppListActivity.newIntent(applicationContext, it))
                    }
                }
            } else {

            }
        })
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
