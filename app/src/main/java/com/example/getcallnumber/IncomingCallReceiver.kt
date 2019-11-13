package com.example.getcallnumber

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager



class IncomingCallReceiver : BroadcastReceiver() {

    companion object {
        val PHONE_NUMBER = "PHONE_NUMBER"
        val ACTION = "INCOMING_CALL_RECEIVER"
    }

    var number: String? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        val state = intent?.getStringExtra(TelephonyManager.EXTRA_STATE)

        try {
            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE, ignoreCase = true)) {
                number = intent?.extras?.getString(TelephonyManager.EXTRA_INCOMING_NUMBER)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }


        val intent = Intent(ACTION)
        intent.putExtra(PHONE_NUMBER, number.toString())
        context?.sendBroadcast(intent)

    }

}
