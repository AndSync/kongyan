package com.wftd.kongyan.activity

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.Toast
import com.wftd.kongyan.R
import com.wftd.kongyan.base.BaseActivity
import com.wftd.kongyan.bean.User
import com.wftd.kongyan.callback.ModifyCallback
import com.wftd.kongyan.utils.CommonUtils
import com.wftd.kongyan.utils.HttpUtils
import com.wftd.kongyan.utils.PhoneUtils
import kotlinx.android.synthetic.main.activity_modify.*


/**
 * Created by liwei on 2018/6/17.
 */
class ModifyPwdActivity : BaseActivity(), View.OnClickListener, ModifyCallback {

    val mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            when (msg.what) {
                1 -> {
                    Toast.makeText(this@ModifyPwdActivity, "修改密码成功", Toast.LENGTH_SHORT).show()
                }
                2 -> {
                    Toast.makeText(this@ModifyPwdActivity, "修改密码失败请确认原密码", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    override fun success() {
        mHandler.sendEmptyMessage(1)
        user.passwordText = check_password.text.toString()
        db.update(user, "passwordText")
//        Toast.makeText(this, "修改密码成功", Toast.LENGTH_SHORT).show()
        finish();
    }

    override fun fail() {

        mHandler.sendEmptyMessage(2)
//        Toast.makeText(this, "修改密码失败", Toast.LENGTH_SHORT).show()
    }

    lateinit var user: User
    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.confirm -> {
                if (CommonUtils.isPassWord(old_password.text.toString())) {
                    if (CommonUtils.isPassWord(new_password.text.toString())) {
                        if (new_password.text.toString().equals(check_password.text.toString())) {
                            if (PhoneUtils.isNetworkAvailable(this@ModifyPwdActivity)) {
                                HttpUtils.modifyGet(user.phoneNumber, check_password.text.toString(), this)
                            } else {
                                Toast.makeText(this, "请检查网络状况", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this, "修改两次密码应该相同", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "新密码应大于6位", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "请输入正确的原始密码", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.back -> {
                finish()
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify)
        user = intent.getSerializableExtra("user") as User

        confirm.setOnClickListener(this)
        back.setOnClickListener(this)
    }
}