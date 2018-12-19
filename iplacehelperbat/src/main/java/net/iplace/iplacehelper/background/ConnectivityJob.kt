package net.iplace.iplacehelper.background

import android.app.Service
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import org.jetbrains.anko.toast

/**
 * Created by ${DANavarro} on 18/12/2018.
 */
class ConnectivityJob : JobService(), ConnectivityReceiver.ConnectivityReceiverListener {




    companion object {
        const val ConnectivityJobId = 5456
    }

    private val mConnectivityReceiver = ConnectivityReceiver(this)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return Service.START_STICKY
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        registerReceiver(mConnectivityReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        unregisterReceiver(mConnectivityReceiver)
        return false
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        val message = if (isConnected) "Conectado" else "No conectado"
        applicationContext.toast(message)
    }
}