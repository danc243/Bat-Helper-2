package net.iplace.iplacehelper


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity

/**
 * Created by ${DANavarro} on 13/12/2018.
 */

open class BaseActivity(private val mainActivity: Class<*>) : AppCompatActivity() {

    companion object {
        const val TIME_OUT_MILLISECONDS: Long = 1000 * 60 * 15
    }

    //region Time out disconnection
    private val disconnectHandler = Handler(Handler.Callback { true })

    private val disconnectCallback = Runnable {
        showActivity(mainActivity, true)
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

    //region Connection Listener

    /*


    private fun scheludeJob() {
        if (HelperPermissions.isReceiveBootCompletedPermission(this)) {
            val job = JobInfo.Builder(ConnectivityJob.ConnectivityJobId, ComponentName(this, ConnectivityJob::class.java))
                    .setRequiresCharging(true)
                    .setMinimumLatency(1000)
                    .setOverrideDeadline(2000)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .setPersisted(true)
                    .build()
            val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            jobScheduler.schedule(job)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        scheludeJob()
    }

    override fun onStop() {
        super.onStop()
//        stopService(Intent(this, ConnectivityJob::class.java))
    }

    override fun onStart() {
        super.onStart()
//        startService(Intent(this, ConnectivityJob::class.java))
    }


*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    //endregion
}