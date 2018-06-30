package com.wftd.kongyan.activity

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.Toast
import com.wftd.kongyan.R
import com.wftd.kongyan.adapter.DataAdapter
import com.wftd.kongyan.base.BaseActivity
import com.wftd.kongyan.callback.QuestionCallback
import com.wftd.kongyan.entity.Question
import com.wftd.kongyan.util.HttpUtils
import kotlinx.android.synthetic.main.activity_data_upfile.all
import kotlinx.android.synthetic.main.activity_data_upfile.all_data
import kotlinx.android.synthetic.main.activity_data_upfile.back
import kotlinx.android.synthetic.main.activity_data_upfile.data_number
import kotlinx.android.synthetic.main.activity_data_upfile.mlitview
import kotlinx.android.synthetic.main.activity_data_upfile.not_up
import org.xutils.common.util.KeyValue
import org.xutils.db.sqlite.WhereBuilder
import java.util.ArrayList

/**
 * Created by liwei on 2018/6/15.
 */
class DataUpActivity : BaseActivity(), View.OnClickListener, QuestionCallback, DataAdapter.OnQuestion {


    val questions = ArrayList<Question>()
    val tempUp = ArrayList<Question>()
    override fun onItemClick(question: Question?) {
        if (question != null) {
            tempUp.add(question)
        }
        HttpUtils.QuestionPost(tempUp, this)
    }

    val mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            when (msg.what) {
                1 -> {
                    Toast.makeText(this@DataUpActivity, "上传数据成功", 1).show()
                }
            }
        }
    }

    override fun fail() {

    }

    override fun success(question: Question?) {
        if (question != null) {
            question.isUpdate = true
            db.update(question, "isUpdate")
            var i = db.update(Question::class.java, WhereBuilder.b("id", "=", question.id), KeyValue("isUpdate", true))
            mHandler.sendEmptyMessage(1)
        }
    }

    override fun success() { //成功后
        mHandler.sendEmptyMessage(1)

        var upQuestion: Question
        var iterator = quests?.iterator()
        while (iterator?.hasNext()!!) {
            upQuestion = iterator.next()
            upQuestion.isUpdate = true
            db.update(upQuestion, "isUpdate")
            var i = db.update(Question::class.java, WhereBuilder.b("id", "=", upQuestion.id),
                KeyValue("isUpdate", true))
        }

    }

    var size: Int = 0
    var tempSize: Int = 0
    var quests: List<Question>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_upfile)
        back.setOnClickListener(this)
        all.setOnClickListener(this)
        not_up.setOnClickListener(this)
        all_data.setOnClickListener(this)
        quests = db.findAll(Question::class.java)
        if (quests == null) {
            size = 0
        } else {
            size = quests?.size!!
        }

        mlitview.adapter = DataAdapter(this, quests)
        (mlitview.adapter as DataAdapter).isUp = true

        (mlitview.adapter as DataAdapter).onItemAddClick = this
        data_number.setText("共有数据" + size)


        if (quests != null) {
            var iterator = quests?.iterator()
            while (iterator?.hasNext()!!) {
                var question = iterator.next()
                if (question.isUpdate == false) {
                    questions.add(question)
                    tempSize++
                }

            }
        }
        if (tempSize > 0) {
            all_data.visibility = View.VISIBLE
        } else {
            all_data.visibility = View.GONE
        }

    }

    override fun onClick(p0: View?) {

        when (p0?.id) {
            R.id.back -> {
                finish()//结束
            }
            R.id.all -> {

                not_up.background = this.getDrawable(R.drawable.shape_data_part_default)
                not_up.setTextColor(Color.GRAY)
                all.background = this.getDrawable(R.drawable.shape_data_all_select)
                all.setTextColor(Color.WHITE)
                data_number.setText("共有数据" + size)
                quests = null;
                quests = db.findAll(Question::class.java)
                if ((mlitview.adapter as DataAdapter).mDataList != null) {
                    (mlitview.adapter as DataAdapter).clear()
                    (mlitview.adapter as DataAdapter).addItem(quests)
                    (mlitview.adapter as DataAdapter).notifyDataSetChanged()
                }
                var iterator = quests?.iterator()
                tempSize = 0
                if (iterator != null) {
                    while (iterator?.hasNext()!!) {
                        var question = iterator.next()
                        if (question.isUpdate == false) {
                            tempSize++
                        }

                    }
                }
                if (tempSize > 0) {
                    all_data.visibility = View.VISIBLE
                } else {
                    all_data.visibility = View.GONE
                }
            }
            R.id.not_up -> {
                all.background = this.getDrawable(R.drawable.shape_data_all_default)
                all.setTextColor(Color.GRAY)
                not_up.background = this.getDrawable(R.drawable.shape_data_part_select)
                not_up.setTextColor(Color.WHITE)

                quests = db.findAll(Question::class.java)
                var iterator = quests?.iterator()
                questions.clear()
                tempSize = 0
                if (iterator != null) {
                    while (iterator?.hasNext()!!) {
                        var question = iterator.next()
                        if (question.isUpdate == false) {
                            questions.add(question)
                            tempSize++
                        }

                    }
                }
                if (tempSize > 0) {
                    all_data.visibility = View.VISIBLE
                } else {
                    all_data.visibility = View.GONE
                }

                data_number.setText("未上传数据" + tempSize)
                (mlitview.adapter as DataAdapter).isUp = false
                if ((mlitview.adapter as DataAdapter) != null) {
                    if ((mlitview.adapter as DataAdapter).mDataList != null) {
                        (mlitview.adapter as DataAdapter).clear()
                        (mlitview.adapter as DataAdapter).addItem(questions)
                        (mlitview.adapter as DataAdapter).notifyDataSetChanged()
                    }
                }
            }

            R.id.all_data -> {
                HttpUtils.QuestionPost(quests, this)
            }


        }


    }


}

