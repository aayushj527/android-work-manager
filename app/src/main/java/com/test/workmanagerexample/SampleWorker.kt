package com.test.workmanagerexample

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

/**
 *  Worker class for logging and showing toast.
 */
class SampleWorker(
    private val appContext: Context,
    private val params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    companion object {
        const val TOAST_TEXT = "TOAST_TEXT"
        const val REQUEST_TYPE = "REQUEST_TYPE"
    }

    override suspend fun doWork(): Result {
        val text = when (params.inputData.getString(REQUEST_TYPE)) {
            RequestType.PERIODIC.name -> params.inputData.getString(TOAST_TEXT) + params.id
            RequestType.ONE_TIME.name -> params.inputData.getString(TOAST_TEXT)
            else -> return Result.failure()
        }

        return text.let {
            if (!it.isNullOrEmpty()) {
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(
                        appContext,
                        it,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                Log.d("WORK_MANAGER_SAMPLE", it)

                return@let Result.success()
            } else {
                return@let Result.failure()
            }
        }
    }

    enum class RequestType {
        PERIODIC, ONE_TIME
    }
}