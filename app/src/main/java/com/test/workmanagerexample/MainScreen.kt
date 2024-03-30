package com.test.workmanagerexample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.workDataOf
import java.util.concurrent.TimeUnit

@Composable
fun MainScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { callWorker() }
        ) {
            Text(text = "Start worker")
        }
    }
}

fun callWorker() {
    val request1 = OneTimeWorkRequestBuilder<SampleWorker>()
        .setInputData(
            workDataOf(
                SampleWorker.TOAST_TEXT to "One time request",
                SampleWorker.REQUEST_TYPE to SampleWorker.RequestType.ONE_TIME.name
            )
        ).build()

    val request2 = PeriodicWorkRequestBuilder<SampleWorker>(15, TimeUnit.MINUTES, 15, TimeUnit.MINUTES)
        .setInputData(
            workDataOf(
                SampleWorker.TOAST_TEXT to "Periodic request",
                SampleWorker.REQUEST_TYPE to SampleWorker.RequestType.PERIODIC.name
            )
        ).build()

    /**
     *  Enqueuing a one time request.
     */
    AppClass.workManager.enqueue(request1)

    /**
     *  Enqueuing a periodic request.
     */
    AppClass.workManager.enqueue(request2)

    /**
     *  Enqueuing a unique periodic request, if request with this name is already running,
     *  no new request will be enqueued (based on ExistingPeriodicWorkPolicy).
     */
    AppClass.workManager.enqueueUniquePeriodicWork(
        "scheduled_work",
        ExistingPeriodicWorkPolicy.KEEP,
        request2
    )
}