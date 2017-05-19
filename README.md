# NetMonitor_Kotlin
一个用Kotlin写的网络监听器
##Screenshots
![](http://7xrqmj.com1.z0.glb.clouddn.com/S60310-222037.jpg?imageView/2/w/300/q/90)


##自定义BroadcastReceiver的onReceive代码 
```kotlin
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
```
##注册网络Receiver
```kotlin
 fun registerNetworkStateReceiver(mContext: Context) {
        val filter = IntentFilter()
        filter.addAction(CUSTOM_ANDROID_NET_CHANGE_ACTION)
        filter.addAction(ANDROID_NET_CHANGE_ACTION)
        mContext.applicationContext.registerReceiver(getReceiver(), filter)
    }
```

##在BaseActivity中开启网络监听 
然后在其实现Activity:HomeActivity中，实现接口方法onNetworkConnected(type)和 onNetworkDisConnected()即可
```kotlin
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
```

