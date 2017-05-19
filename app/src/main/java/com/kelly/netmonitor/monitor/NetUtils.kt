package com.kelly.netmonitor.monitor

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import java.util.*

/**
 * Created by zongkaili on 2017/5/19.
 */
class NetUtils {
    enum class NetType {
        WIFI, CMNET, CMWAP, NONE
    }


    //静态方法需要写在 companion object 块中
    companion object{
        fun isNetworkAvailable(context: Context?): Boolean {
            val mgr = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = mgr?.allNetworkInfo
            return info?.indices!!.any { info[it].state == NetworkInfo.State.CONNECTED }
        }

        fun isNetworkConnected(context: Context?): Boolean {
            val mConnectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mNetworkInfo = mConnectivityManager?.activeNetworkInfo
            return mNetworkInfo?.isAvailable!!
        }

        fun isWifiConnected(context: Context?): Boolean {
            val mConnectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            return mWiFiNetworkInfo?.isAvailable!!
        }

        fun isMobileConnected(context: Context?): Boolean {
            val mConnectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            return mMobileNetworkInfo?.isAvailable!!
        }

        fun getConnectedType(context: Context?): Int {
            val mConnectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mNetworkInfo = mConnectivityManager.activeNetworkInfo
            return mNetworkInfo?.type!! -1
        }

        fun getAPNType(context: Context?): NetType {
            val connMgr = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connMgr.activeNetworkInfo ?: return NetType.NONE
            val nType = networkInfo.type

            if (nType == ConnectivityManager.TYPE_MOBILE) {
                if (networkInfo.extraInfo.toLowerCase(Locale.getDefault()) == "cmnet") {
                    return NetType.CMNET
                } else {
                    return NetType.CMWAP
                }
            } else if (nType == ConnectivityManager.TYPE_WIFI) {
                return NetType.WIFI
            }
            return NetType.NONE
        }

    }

}