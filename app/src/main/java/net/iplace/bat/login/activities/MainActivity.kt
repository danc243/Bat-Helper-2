package net.iplace.bat.login.activities


import android.arch.persistence.room.Room
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import net.iplace.bat.login.R
import net.iplace.iplacehelper.HelperUtils
import net.iplace.iplacehelper.database.AppDatabase
import net.iplace.iplacehelper.database.Converters
import net.iplace.iplacehelper.database.unidad.Unidad
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
                        finish()
                    }
                }
            } else {
                // Todo campos no válidos, resaltar el error. Creo que ya lo hace Helper.validateEditText
            }
        })

        pruebasDataBase()

    }

    fun pruebasDataBase() {
        val db = Room.databaseBuilder(this, AppDatabase::class.java, AppDatabase.db_name).build()

        val s = "[\n" +
                "   {\n" +
                "      \"id\": 123,\n" +
                "      \"clave\": \"j300\",\n" +
                "      \"marca\": \"dasdsa\",\n" +
                "      \"idTransportista\": 777,\n" +
                "      \"modelo\": \"klol\",\n" +
                "      \"color\": \"rojo\",\n" +
                "      \"placas\": \"dsadas82484adsa\"\n" +
                "   }\n" +
                "]"






    }


    companion object {
        fun newInstance(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
    }


}
