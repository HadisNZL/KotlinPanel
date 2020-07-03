package com.kotlin.panel.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.kotlin.panel.event.NetworkChangeEvent
import com.kotlin.panel.utils.NetWorkUtil
import org.greenrobot.eventbus.EventBus

class NetworkChangeReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {
        val isConnected = NetWorkUtil.isNetworkConnected(context)
        if (isConnected) {
            EventBus.getDefault().post(NetworkChangeEvent(isConnected))
        }
        Log.e("sdjsdklsd", "--->$isConnected")
    }

}