package net.iplace.bat.login.activities


import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import net.iplace.bat.login.R
import net.iplace.bat.login.retrofit.HelperRetrofit
import net.iplace.iplacehelper.HelperPermissions
import net.iplace.iplacehelper.HelperUtils

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        Picasso.setSingletonInstance(Picasso.Builder(this).build())

        btn_login.setOnClickListener {
            getIMEI {
                it?.let { imei ->
                    if (HelperUtils.validateEditText(arrayOf(et_login_user, et_login_pass))) {
                        HelperRetrofit.login(this, et_login_user.text.toString(), et_login_pass.text.toString(), imei) {
                            it?.let { login ->
                                startActivity(AppListActivity.newIntent(applicationContext, login))
                                finish()
                            }
                        }
                    }
                }
            }
        }

        img_fondo()
    }


    fun getIMEI(imei: (imei: String?) -> Unit) {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.READ_PHONE_STATE)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        imei(HelperUtils.getIMEI(this@MainActivity))
                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {

                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    }
                })
                .check()
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
        savedInstanceState?.let {
            et_login_user.setText(it.getString("login", ""))
            et_login_pass.setText(it.getString("pass", ""))
        }
    }

    private fun img_fondo() {


        login_iv_background?.let {
            //            Glide.with(this)
////                    .load(R.drawable.ic_login_background)
////                    .apply(RequestOptions().placeholder(R.drawable.ic_login_box))
//                    .load(ContextCompat.getDrawable(this, R.drawable.ic_login_background))
//                    .into(it)

            Picasso.get().load(R.drawable.ic_login_background)
                    .fit()
                    .into(login_iv_background)
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
