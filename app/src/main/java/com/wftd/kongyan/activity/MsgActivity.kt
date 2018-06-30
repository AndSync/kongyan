package com.wftd.kongyan.activity

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import com.wftd.kongyan.R
import com.wftd.kongyan.base.BaseActivity
import com.wftd.kongyan.bean.Message
import com.wftd.kongyan.bean.User
import com.wftd.kongyan.callback.MessageCallback
import com.wftd.kongyan.utils.HttpUtils
import kotlinx.android.synthetic.main.activity_message.mylistview
import kotlinx.android.synthetic.main.activity_modify.back

/**
 * Created by liwei on 2018/6/19.
 */
class MsgActivity : BaseActivity(), View.OnClickListener, MessageCallback {

    val mHandler = object : Handler() {
        override fun handleMessage(msg: android.os.Message) {
            super.handleMessage(msg)

            when (msg.what) {
                1 -> {
                    Toast.makeText(this@MsgActivity, "暂无数据", 1).show()
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
        var user = intent.getSerializableExtra("user")
        HttpUtils.MessageGet((user as User).id, this@MsgActivity)
        mylistview.adapter = MessageAdapter(this@MsgActivity)
        back.setOnClickListener(this)
    }
}