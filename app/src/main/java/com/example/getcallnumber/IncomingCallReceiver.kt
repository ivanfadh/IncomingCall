package com.example.getcallnumber

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.util.Log

class IncomingCallReceiver : BroadcastReceiver() {

    companion object {
        val PHONE_NUMBER = "PHONE_NUMBER"
        val ACTION = "INCOMING_CALL_RECEIVER"
    }

    var number: String? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        number = intent?.extras?.getString(TelephonyManager.EXTRA_INCOMING_NUMBER)
        val intent = Intent(ACTION)
        intent.putExtra(PHONE_NUMBER, number.toString())
        context?.sendBroadcast(intent)

    }

  /*  interface ReceiverInterface {
        fun onReceive(phoneNumber: String)
    }*/
}
