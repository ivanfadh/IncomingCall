package com.example.getcallnumber

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.widget.Toast

class IncomingCallReceiver : BroadcastReceiver() {

    companion object {
        val INTENT = "com.example.getcallnumber.intent.TEST"
    }

    override fun onReceive(context: Context?, intent: Intent?) {

        try {
            val state = intent?.getStringExtra(TelephonyManager.EXTRA_STATE)
            val number = intent?.extras?.getString(TelephonyManager.EXTRA_INCOMING_NUMBER)

            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING, ignoreCase = true)) {
                Toast.makeText(context, "Ring $number", Toast.LENGTH_LONG).show()
            }
            if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK, ignoreCase = true)) {
                Toast.makeText(context, "Answered $number", Toast.LENGTH_SHORT).show()
            }
            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE, ignoreCase = true)) {
                Toast.makeText(context, "Idle $number", Toast.LENGTH_SHORT).show()
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }


        val intent = Intent()
        intent.action = INTENT
        intent.putExtra("message", intent.extras?.getString(TelephonyManager.EXTRA_INCOMING_NUMBER))
//        intent.extras?.getString(TelephonyManager.EXTRA_INCOMING_NUMBER)
        context?.sendBroadcast(intent)

    }

  /*  interface ReceiverInterface {
        fun onReceive(phoneNumber: String)
    }*/
}
