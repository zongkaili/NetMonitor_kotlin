package com.kelly.netmonitor.monitor

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import java.util.*

/**
 * Created by zongkaili on 2017/5/19.
 */
object NetStateReceiver : BroadcastReceiver() {
    private val TAG = NetStateReceiver::class.java.simpleName
    private val CUSTOM_ANDROID_NET_CHANGE_ACTION = "CUSTOM_ANDROID_NET_CHANGE_ACTION"
    private val ANDROID_NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE"

    private var isNetAvailable: Boolean = false
    private var mNetType: NetUtils.NetType? = null
    private var mNetChangeObservers: ArrayList<NetChangeObserver>? = ArrayList()
    private var mBroadcastReceiver: BroadcastReceiver? = null

    private fun getReceiver(): BroadcastReceiver? {
        if (null == mBroadcastReceiver) {
            synchronized(NetStateReceiver::class.java) {
                if (null == mBroadcastReceiver) {
                    mBroadcastReceiver = NetStateReceiver
                }
            }
        }
        return mBroadcastReceiver
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        mBroadcastReceiver = this
        if (intent?.getAction().equals(ANDROID_NET_CHANGE_ACTION,true)
                || intent?.getAction().equals(CUSTOM_ANDROID_NET_CHANGE_ACTION,true)) {
            if (!NetUtils.isNetworkAvailable(context)) {
                Log.e(this.javaClass.getName(), "<--- network disconnected --->");
                isNetAvailable = false
            } else {
                Log.e(this.javaClass.getName(), "<--- network connected --->");
                isNetAvailable = true
                mNetType = NetUtils.getAPNType(context)
            }
            notifyObserver()
        }
    }

    /**
     * 注册

     * @param mContext
     */
    fun registerNetworkStateReceiver(mContext: Context) {
        val filter = IntentFilter()
        filter.addAction(CUSTOM_ANDROID_NET_CHANGE_ACTION)
        filter.addAction(ANDROID_NET_CHANGE_ACTION)
        mContext.applicationContext.registerReceiver(getReceiver(), filter)
    }

    /**
     * 清除

     * @param mContext
     */
    fun checkNetworkState(mContext: Context) {
        val intent = Intent()
        intent.action = CUSTOM_ANDROID_NET_CHANGE_ACTION
        mContext.sendBroadcast(intent)
    }

    /**
     * 解除注册

     * @param mContext
     */
    fun unRegisterNetworkStateReceiver(mContext: Context) {
            try {
                mContext.applicationContext.unregisterReceiver(mBroadcastReceiver!!)
            } catch (e: Exception) {

            }

    }

    fun getAPNType(): NetUtils.NetType? {
        return mNetType
    }

    private fun notifyObserver() {
        if (!mNetChangeObservers?.isEmpty()!!) {
            val size = mNetChangeObservers?.size
            for (i in 0..size!! - 1) {
                val observer = mNetChangeObservers?.get(i)
                if (observer != null) {
                    if (isNetworkAvailable()) {
                        observer.onNetConnected(mNetType)
                    } else {
                        observer.onNetDisConnect()
                    }
                }
            }
        }
    }

    fun isNetworkAvailable(): Boolean {
        return isNetAvailable
    }

    /**
     * 添加网络监听

     * @param observer
     */
    fun registerObserver(observer: NetChangeObserver?) {
        if (mNetChangeObservers == null) {
            mNetChangeObservers = ArrayList<NetChangeObserver>()
        }
        mNetChangeObservers?.add(observer!!)
    }


    fun removeRegisterObserver(observer: NetChangeObserver){
        if (mNetChangeObservers?.contains(observer)!!) {
            mNetChangeObservers?.remove(observer)
        }
    }
}