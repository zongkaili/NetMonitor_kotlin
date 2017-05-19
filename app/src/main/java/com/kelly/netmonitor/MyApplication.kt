package com.kelly.netmonitor

import android.app.Application
import com.kelly.netmonitor.monitor.NetStateReceiver

/**
 * Created by zongkaili on 2017/5/19.
 */
class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        //开启网络监听
        NetStateReceiver.registerNetworkStateReceiver(this)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        NetStateReceiver.unRegisterNetworkStateReceiver(this)
        android.os.Process.killProcess(android.os.Process.myPid())
    }

}