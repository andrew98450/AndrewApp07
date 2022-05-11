package com.andrew.andrewapp_07

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var tvTime : TextView
    private lateinit var btnStart : Button
    private val receive : BroadcastReceiver = object : BroadcastReceiver()  {
        override fun onReceive(p0: Context?, p1: Intent?) {

            p1?.extras?.let {

                tvTime.text = String.format("%02d:%02d:%02d",
                    it.getInt("H"),
                    it.getInt("M"),
                    it.getInt("S"))
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnStart = findViewById(R.id.btn_start)
        tvTime = findViewById(R.id.tv_time)
        val filter = IntentFilter("MyMessage")
        registerReceiver(receive, filter)
        var flag  = false
        btnStart.setOnClickListener{
            flag = !flag
            btnStart.text = if (flag) "Stop" else "Start"
            startService(Intent(this, MyService::class.java).putExtra("flag", flag))
            if (flag)
                Toast.makeText(this, "Starting...", Toast.LENGTH_LONG).show()
            else
                Toast.makeText(this, "Stopping...", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receive)
    }
}