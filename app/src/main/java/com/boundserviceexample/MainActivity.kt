package com.boundserviceexample

import android.app.Activity
import com.boundserviceexample.MyLocalService
import android.content.ServiceConnection
import android.content.ComponentName
import android.os.IBinder
import com.boundserviceexample.MyLocalService.LocalBinder
import android.os.Bundle
import com.boundserviceexample.R
import android.content.Intent
import android.view.View
import android.widget.Toast

class MainActivity : Activity() {
    var localService: MyLocalService? = null
    private var isBound = false
    private val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as LocalBinder
            localService = binder.service
            isBound = true
        }
        override fun onServiceDisconnected(arg0: ComponentName) {
            isBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this, MyLocalService::class.java)
        bindService(intent, connection, BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        if (isBound) {
            unbindService(connection)
            isBound = false
            Toast.makeText(this, "Binding is false now!", Toast.LENGTH_SHORT).show()
        }
    }

    fun displayDate(v: View?) {
        if (isBound) {
            val date = localService!!.currentDate
            Toast.makeText(this, date.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}