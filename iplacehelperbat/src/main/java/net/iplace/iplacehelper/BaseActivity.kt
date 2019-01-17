package net.iplace.iplacehelper


import android.content.Intent
import android.os.Handler
import android.support.v7.app.AppCompatActivity

/**
 * Created by ${DANavarro} on 13/12/2018.
 */

open class BaseActivity(private val mainActivity: Class<*>) : AppCompatActivity() {

    companion object {
        const val TIME_OUT_MILLISECONDS: Long = 1000 * 60 * 15
        const val Cambio = "Cambio"
    }

    //region Time out disconnection
    private val disconnectHandler = Handler(Handler.Callback { true })

    private val disconnectCallback = Runnable {
        //        showActivity(mainActivity, true)
        f()
    }

    fun f() {
        finish()
    }

    fun showActivity(to: Class<*>, finish: Boolean) {
        val intent = Intent(this, to)
        startActivity(intent)
        if (finish) {
            finish()
        }
    }

    fun resetDisconnectTimer() {
        disconnectHandler.removeCallbacks(disconnectCallback)
        disconnectHandler.postDelayed(disconnectCallback, TIME_OUT_MILLISECONDS)
    }

    fun stopDisconnectTimer() {
        disconnectHandler.removeCallbacks(disconnectCallback)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopDisconnectTimer()
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        resetDisconnectTimer()
    }

    //endregion
}