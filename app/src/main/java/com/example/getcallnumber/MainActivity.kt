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


class MainActivity : AppCompatActivity() {

    private val PERMISSION_REQUEST_READ_PHONE_STATE = 1
    private val REQUEST_ID = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val getNumber: TextView = findViewById(R.id.number)
        getNumber.setOnClickListener {
//            requestRole()
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED ||
                checkSelfPermission(Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_DENIED
            ) {
                val permissions = arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CALL_LOG)
                requestPermissions(permissions, PERMISSION_REQUEST_READ_PHONE_STATE)
            }
        }
    }

    fun requestRole() {
        val roleManager: RoleManager = getSystemService(Context.ROLE_SERVICE)
        val intent = roleManager.createRequestRoleIntent("android.app.role.CALL_SCREENING")
        startActivityForResult(intent, REQUEST_ID)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ID) {
            if (resultCode == android.app.Activity.RESULT_OK) {
                // Your app is now the call screening app
            } else {
                // Your app is not the call screening app
            }
        }
    }
}
