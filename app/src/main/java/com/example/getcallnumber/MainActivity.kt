package com.example.getcallnumber

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.content.Intent
import android.content.IntentFilter
import android.telecom.Call
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import android.app.role.RoleManager
import android.util.Log
import org.w3c.dom.Text


class MainActivity : AppCompatActivity() {

    private val PERMISSION_REQUEST_READ_PHONE_STATE = 1
    private var phoneReceiver: PhoneReceiver? = null

    var getNumber: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getNumber = findViewById(R.id.number)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED ||
                checkSelfPermission(Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_DENIED
            ) {
                val permissions = arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CALL_LOG)
                requestPermissions(permissions, PERMISSION_REQUEST_READ_PHONE_STATE)
            }
        }
    }

    //Register BroadcastReceiver
    override fun onResume() {
        super.onResume()
        if(phoneReceiver == null){
            phoneReceiver = PhoneReceiver()
            val intentFilter = IntentFilter(IncomingCallReceiver.ACTION)
            registerReceiver(phoneReceiver, intentFilter)

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(phoneReceiver!= null)
            unregisterReceiver(phoneReceiver)
    }

    fun updateUI(phoneNumber: String?){
        getNumber?.text = phoneNumber
    }

    class PhoneReceiver: BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            val activity = context as MainActivity?
            activity?.updateUI(intent?.getStringExtra(IncomingCallReceiver.PHONE_NUMBER))
        }

    }
}
