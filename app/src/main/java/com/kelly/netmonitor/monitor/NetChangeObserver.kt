package com.kelly.netmonitor.monitor

/**
 * Created by zongkaili on 2017/5/19.
 */
interface NetChangeObserver {

    /**
     * 网络连接回调
     * type:网络类型
     */
    fun onNetConnected(type: NetUtils.NetType?)

    /**
     * 没有网络的回调
     */
    fun onNetDisConnect()
}

