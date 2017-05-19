package com.kelly.netmonitor.activity

import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.kelly.netmonitor.R
import com.kelly.netmonitor.monitor.NetUtils

/**
 * Created by zongkaili on 2017/5/19.
 */
class HomeActivity : BaseActivity() {
    private var mTvState: TextView? = null
    private var mRlContent: RelativeLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mTvState = findViewById(R.id.tv_state) as TextView
        mRlContent = findViewById(R.id.rl_state_content) as RelativeLayout
    }

    override fun onNetworkConnected(type: NetUtils.NetType?) {
        mTvState?.setText("网络连接正常,  网络类型 ： $type");
        mRlContent?.setVisibility(View.GONE);
    }

    override fun onNetworkDisConnected() {
        mTvState?.setText("网络连接断开");
        mRlContent?.setVisibility(View.VISIBLE);
    }
}
