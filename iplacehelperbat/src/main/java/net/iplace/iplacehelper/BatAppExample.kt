package net.iplace.iplacehelper

import android.app.Application
import com.evernote.android.job.JobManager
import net.iplace.iplacehelper.background.PetitionJobCreatorExample

/**
 * Created by ${DANavarro} on 18/12/2018.
 */
class BatAppExample : Application() {
    override fun onCreate() {
        super.onCreate()
        JobManager.create(this).addJobCreator(PetitionJobCreatorExample())
    }
}