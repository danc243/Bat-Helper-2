package net.iplace.bat.login.activities


import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import net.iplace.bat.login.R
import net.iplace.iplacehelper.IplaceHelper

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        tv_login.setOnClickListener(View.OnClickListener {
            startActivity(AppListActivity.newIntent(applicationContext))

//            IplaceHelper.login({
//
//            }, this, )

        })

    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }
}
