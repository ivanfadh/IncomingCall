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
import android.content.pm.PermissionInfo
import android.util.Log
import org.w3c.dom.Text


class MainActivity : AppCompatActivity() {

    private val PERMISSION_REQUEST = 1
    private var phoneReceiver: PhoneReceiver? = null

    companion object {
        const val REQUEST_CODE = 1
    }

    var getNumber: TextView? = null
    var phoneNumber: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getNumber = findViewById(R.id.number)
2
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q){
            requestRole()
        } else {
            checkPermission()
        }

    }

    private fun checkPermission(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED ||
                checkSelfPermission(Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_DENIED
            ) {
                val permissions = arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CALL_LOG)
                requestPermissions(permissions, PERMISSION_REQUEST)
            }
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Permission NOT granted!", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }

    }

    private fun requestRole() {
        val roleManager = getSystemService(Context.ROLE_SERVICE) as RoleManager
        val intent = roleManager.createRequestRoleIntent("android.app.role.CALL_SCREENING")
        startActivityForResult(intent, PERMISSION_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK)
                Toast.makeText(this, "ROLE_SERVICE granted!", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "ROLE_SERVICE not granted!", Toast.LENGTH_SHORT).show()
        }
    }

    //Register BroadcastReceiver
    override fun onResume() {
        super.onResume()
        if(phoneReceiver == null){
            phoneReceiver = PhoneReceiver()
            var intentFilter: IntentFilter?
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q){
                intentFilter = IntentFilter(CallScreeningServiceImpl.ACTION)
            } else {
                intentFilter = IntentFilter(IncomingCallReceiver.ACTION)
            }
            registerReceiver(phoneReceiver, intentFilter)

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(phoneReceiver!= null)
            unregisterReceiver(phoneReceiver)
    }

    fun updateUI(phoneNumber: String?){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q){
            getNumber?.text = phoneNumber?.substring(4)
        } else {
            getNumber?.text = phoneNumber
        }
    }

    class PhoneReceiver: BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            val activity = context as MainActivity?
            activity?.updateUI(intent?.getStringExtra(IncomingCallReceiver.PHONE_NUMBER))
        }

    }
}
