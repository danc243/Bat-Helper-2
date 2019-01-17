package net.iplace.iplacehelper.background

import android.util.Log
import com.evernote.android.job.Job
import com.evernote.android.job.JobCreator
import com.evernote.android.job.JobRequest
import okhttp3.*
import java.io.IOException

/**
 * Created by ${DANavarro} on 18/12/2018.
 */
class PetitionJobCreatorExample : JobCreator {
    override fun create(tag: String): Job? {
        when (tag) {
            ReqresDemoJob.TAG -> {
                return ReqresDemoJob()
            }
        }
        return null
    }
}


class ReqresDemoJob : Job() {
    companion object {
        const val TAG = "DASDSA"

        fun schedule() {
            JobRequest.Builder(ReqresDemoJob.TAG)
                    .setExact(1_000L)
                    .setRequiresCharging(false)
                    .setRequiresDeviceIdle(false)
                    .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                    .setRequirementsEnforced(true)
                    .setUpdateCurrent(false)
                    .build()
                    .schedule()
        }
    }

    override fun onRunJob(params: Params): Result {

        val JSON = MediaType.parse("application/json; charset=utf-8")

        val url = "https://reqres.in/api/users"

        val json = """{
    "operador": "Prueba222",
    "alg222222222o": 444,
    "id": "261",
    "createdAt": "2018-12-19T15:37:09.812Z"
}"""

        val body = RequestBody.create(JSON, json)
        val request = Request.Builder()
                .url(url)
                .method("POST", body)
                .build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                Log.d("PRUEBA", "ERROR")
            }

            override fun onResponse(call: Call?, response: Response?) {
                Log.d("PRUEBA", response?.body()?.string())
            }
        })
        return Result.SUCCESS
    }

    override fun onReschedule(newJobId: Int) {
        Log.d("PRUEBA", "onReschedule")
    }
}