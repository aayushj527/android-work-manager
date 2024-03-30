package com.test.workmanagerexample

import android.app.Application
import androidx.work.WorkManager

class AppClass: Application() {

    companion object {
        lateinit var workManager: WorkManager
    }

    override fun onCreate() {
        super.onCreate()

        /**
         *  Instantiating the work manager.
         */
        workManager = WorkManager.getInstance(this)
    }
}