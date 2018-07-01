package com.wftd.kongyan.activity

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import com.wftd.kongyan.R
import com.wftd.kongyan.adapter.MessageAdapter
import com.wftd.kongyan.app.UserHelper
import com.wftd.kongyan.base.BaseActivity
import com.wftd.kongyan.callback.MessageCallback
import com.wftd.kongyan.entity.Message
import com.wftd.kongyan.entity.People
import com.wftd.kongyan.util.HttpUtils
import kotlinx.android.synthetic.main.activity_message.back
import kotlinx.android.synthetic.main.activity_message.mylistview

/**
 * Created by liwei on 2018/6/19.
 */
class MsgActivity : BaseActivity(), View.OnClickListener, MessageCallback {

    val mHandler = object : Handler() {
        override fun handleMessage(msg: android.os.Message) {
            super.handleMessage(msg)

            when (msg.what) {
                1 -> {
                    Toast.makeText(this@MsgActivity, "暂无数据", Toast.LENGTH_LONG).show()
                }
                2 -> {
                    (mylistview.adapter as MessageAdapter).notifyDataSetChanged()
                }
            }
        }
    }

    override fun success(messages: MutableList<Message>?): Boolean {
        if (messages != null) {
            if (messages.size > 0) {
                (mylistview.adapter as MessageAdapter).addItem(messages)
                mHandler.sendEmptyMessage(2)
            }
        } else {
            mHandler.sendEmptyMessage(1)
        }
        return true
    }

    override fun fail(): Boolean {
        return false
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.back -> {
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        var user = UserHelper.getUserInfo()
        HttpUtils.MessageGet((user as People).id, this@MsgActivity)
        mylistview.adapter = MessageAdapter(this@MsgActivity)
        back.setOnClickListener(this)
    }
}