package com.example.getcallnumber

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NumberReceiver(val receiverInterface: ReceiverInterface) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == IncomingCallReceiver.INTENT) {
//            getNumber.text = intent.getStringExtra("message")
            receiverInterface.onReceive(intent.getStringExtra("message"))
        }
    }

    interface ReceiverInterface{
        fun onReceive(numberReceiver: String)
    }
}

