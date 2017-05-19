package com.kelly.netmonitor.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.kelly.netmonitor.monitor.NetChangeObserver
import com.kelly.netmonitor.monitor.NetStateReceiver
import com.kelly.netmonitor.monitor.NetUtils

/**
 * Created by zongkaili on 2017/5/19.
 */
abstract class BaseActivity : AppCompatActivity() {
    protected var mNetChangeObserver: NetChangeObserver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mNetChangeObserver = object : NetChangeObserver {
            override fun onNetConnected(type: NetUtils.NetType?) {
                onNetworkConnected(type)
            }

            override fun onNetDisConnect() {
                onNetworkDisConnected()
            }
        }

        //开启广播监听网络改变
        NetStateReceiver.registerObserver(mNetChangeObserver)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    /**
     * 网络连接状态
     * @param type 网络状态
     */
    protected abstract fun onNetworkConnected(type: NetUtils.NetType?)

    /**
     * 网络断开的时候调用
     */
    protected abstract fun onNetworkDisConnected()
}
