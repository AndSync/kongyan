package com.wftd.kongyan.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import com.wftd.kongyan.R;
import com.wftd.kongyan.adapter.DataAdapter;
import com.wftd.kongyan.base.BaseActivity;
import com.wftd.kongyan.callback.QuestionCallback;
import com.wftd.kongyan.entity.Question;
import com.wftd.kongyan.util.DialogUtils;
import com.wftd.kongyan.util.HttpUtils;
import com.wftd.kongyan.util.LogUtils;
import com.wftd.kongyan.util.PhoneUtils;
import com.wftd.kongyan.util.ToastUtils;
import java.util.ArrayList;
import java.util.List;
import org.xutils.common.util.KeyValue;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

/**
 * @author AndSync
 * @date 2018/7/1
 * Copyright © 2014-2018 AndSync All rights reserved.
 */
public class DataManagerActivity extends BaseActivity implements View.OnClickListener, QuestionCallback {

    private ImageView mIvBack;
    private RadioButton mRbAll;
    private RadioButton mRbNotUp;
    private TextView mTvDataCount;
    private Button mBtAllUpload;
    private ListView mListView;
    private List<Question> dataList = new ArrayList<>();
    private DataAdapter mAdapter;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_upfile);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mRbAll = (RadioButton) findViewById(R.id.all);
        mRbNotUp = (RadioButton) findViewById(R.id.not_up);
        mTvDataCount = (TextView) findViewById(R.id.data_number);
        mBtAllUpload = (Button) findViewById(R.id.all_data);
        mListView = (ListView) findViewById(R.id.mlitview);
        mIvBack.setOnClickListener(this);
        mRbAll.setOnClickListener(this);
        mRbNotUp.setOnClickListener(this);
        mBtAllUpload.setOnClickListener(this);
        mAdapter = new DataAdapter(this, dataList);
        mListView.setAdapter(mAdapter);
        mRbAll.performClick();
        mAdapter.setOnAddClickListener(new DataAdapter.OnQuestion() {
            @Override
            public void onItemClick(Question question) {
                if (PhoneUtils.isNetworkAvailable(context)) {
                    List<Question> list = new ArrayList<>();
                    list.add(question);
                    HttpUtils.QuestionPost(list, DataManagerActivity.this);
                } else {
                    DialogUtils.showAlertDialog(context, "上传失败", "设备未连接网络，请连接后上传");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.all:
                index = 0;
                mRbAll.setChecked(true);
                mRbNotUp.setChecked(false);
                mBtAllUpload.setVisibility(View.GONE);
                try {
                    List dbList = db.findAll(Question.class);
                    if (dbList != null) {
                        dataList.clear();
                        dataList.addAll(dbList);
                        mTvDataCount.setText("共有数据" + dbList.size() + "条");
                        LogUtils.d(TAG, dbList.toString());
                        mAdapter.notifyDataSetChanged();
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.not_up:
                index = 1;
                mRbAll.setChecked(false);
                mRbNotUp.setChecked(true);
                try {
                    List dbList = db.selector(Question.class).where("isUpdate", "=", false).findAll();
                    if (dbList != null) {
                        dataList.clear();
                        dataList.addAll(dbList);
                        mAdapter.notifyDataSetChanged();
                        mTvDataCount.setText("未上传数据" + dbList.size() + "条");
                        if (dataList.size() > 0) {
                            mBtAllUpload.setVisibility(View.VISIBLE);
                        } else {
                            mBtAllUpload.setVisibility(View.GONE);
                        }
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.all_data:
                if (PhoneUtils.isNetworkAvailable(context)) {
                    HttpUtils.QuestionPost(dataList, this);
                } else {
                    DialogUtils.showAlertDialog(context, "上传失败", "设备未连接网络，请连接后上传");
                }

                break;
            default:
                break;
        }
    }

    @Override
    public void success() {
        //全部上传成功
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    db.update(Question.class, WhereBuilder.b("isUpdate", "=", false), new KeyValue("isUpdate", true));
                    dataList.clear();
                    mAdapter.notifyDataSetChanged();
                    ToastUtils.show(context, "上传成功");
                    mBtAllUpload.setVisibility(View.GONE);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void success(final Question question) {
        //单条上传成功
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    db.update(Question.class, WhereBuilder.b("id", "=", question.getId()),
                        new KeyValue("isUpdate", true));
                    for (int i = 0; i < dataList.size(); i++) {
                        Question oldQuestion = dataList.get(i);
                        if (oldQuestion.getId() == question.getId()) {
                            if (index == 1) {
                                dataList.remove(oldQuestion);
                            } else {
                                oldQuestion.setUpdate(true);
                            }
                            break;
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                    ToastUtils.show(context, "上传成功");
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void fail() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.show(context, "上传失败");
            }
        });
    }
}
