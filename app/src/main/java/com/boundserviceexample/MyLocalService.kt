package com.boundserviceexample

import android.app.Service
import android.os.IBinder
import com.boundserviceexample.MyLocalService.LocalBinder
import com.boundserviceexample.MyLocalService
import android.content.Intent
import android.os.Binder
import java.util.*

class MyLocalService : Service() {
    private val binder: IBinder = LocalBinder()

    // need to make the service bindable
    inner class LocalBinder : Binder() {
        val service: MyLocalService
            get() = this@MyLocalService
    }

    override fun onBind(intent: Intent): IBinder? {
        return binder
    }

    val currentDate: Date
        get() = Calendar.getInstance().time
}