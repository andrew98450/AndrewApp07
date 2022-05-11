package com.andrew.andrewapp_07

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder

class MyService : Service() {
    private var h = 0
    private var m = 0
    private var s = 0
    private var flag = false
    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        intent?.extras?.let {
            flag = it.getBoolean("flag")
        }
        object : Thread() {
            override fun run() {
                while (flag) {
                    sleep(1000)
                    s++
                    if (s >= 60) {
                        s = 0
                        m++
                        if (m >= 60) {
                            m = 0
                            h++
                        }
                    }
                    val intentBroad = Intent("MyMessage")
                    val bundle = Bundle()
                    bundle.putInt("H", h)
                    bundle.putInt("M", m)
                    bundle.putInt("S", s)
                    intentBroad.putExtras(bundle)
                    sendBroadcast(intentBroad)
                }
            }
        }.start()

        return START_STICKY
    }
}