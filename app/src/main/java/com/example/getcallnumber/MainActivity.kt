package com.example.getcallnumber

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.content.Intent
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    private val PERMISSION_REQUEST_READ_PHONE_STATE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val getNumber: TextView = findViewById(R.id.number)

        Toast.makeText(this, "Started the app", Toast.LENGTH_SHORT).show()

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED ||
                checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED
            ) {
                val permissions = arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE)
                requestPermissions(permissions, PERMISSION_REQUEST_READ_PHONE_STATE)
            }
        }

//        val receiver = IncomingCallReceiver()

        class NumberReceiver: BroadcastReceiver(){

            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action == IncomingCallReceiver.INTENT) {
                    getNumber.text = intent.getStringExtra("message")
                }
            }
        }
    }

    /*override fun onReceive(phoneNumber: String) {
        number.text = phoneNumber
    }*/
}
