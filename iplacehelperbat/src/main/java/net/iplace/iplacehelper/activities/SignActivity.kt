package net.iplace.iplacehelper.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem
import net.iplace.iplacehelper.R
import net.iplace.iplacehelper.paint.PaintView
import java.io.ByteArrayOutputStream


class SignActivity : AppCompatActivity() {
    companion object {
        private const val INTENT_BITMAP_RESULT = "result_image"
        const val requestCode = 211
        const val custodio_sign = 333
        const val operador_sign = 444

        fun getImageIntent(data: Intent?): Bitmap? {
            if (data == null) return null
            val array = data.getByteArrayExtra(INTENT_BITMAP_RESULT)
            return BitmapFactory.decodeByteArray(array, 0, array.size)
        }
    }

    private lateinit var paintView: PaintView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)
        paintView = findViewById(R.id.viewSign)
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        paintView.init(metrics)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.sign_activity_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.sign_activity_menu_reset -> {
                paintView.clear()
                true
            }
            R.id.sign_activity_menu_ready -> {
                ready()
                true
            }
            else -> false
        }
    }

    private fun bitmapToByteArray(): ByteArray? {
        val stream = ByteArrayOutputStream()
        val temp = paintView.getDraw()
        temp.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }


    private fun ready() {
        val intent = Intent()
        bitmapToByteArray()?.let {
            intent.putExtra(INTENT_BITMAP_RESULT, it)
        }
        setResult(RESULT_OK, intent)
        finish()
    }
}
