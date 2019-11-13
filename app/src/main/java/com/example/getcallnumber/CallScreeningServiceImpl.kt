package com.example.getcallnumber

import android.content.Intent
import android.telecom.Call
import android.telecom.CallScreeningService
import android.telecom.TelecomManager
import android.telephony.TelephonyManager
import android.widget.Toast



class CallScreeningServiceImpl : CallScreeningService() {

    companion object {
        val ACTION = "CALL_SCREENING_ACTION"
        val PHONE_NUMBER = "PHONE_NUMBER"
    }
    override fun onScreenCall(callDetails: Call.Details) {
        val intent = Intent(ACTION)
        intent.putExtra(PHONE_NUMBER, callDetails.handle.toString())
        sendBroadcast(intent)
    }
}