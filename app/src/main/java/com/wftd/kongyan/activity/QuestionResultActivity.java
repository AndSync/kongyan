package com.wftd.kongyan.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.wftd.kongyan.R;
import com.wftd.kongyan.base.BaseActivity;
import com.wftd.kongyan.entity.Result;
import com.wftd.kongyan.util.AidlUtil;
import com.wftd.kongyan.util.ConvertUtils;
import com.wftd.kongyan.util.ToastUtils;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 调查结果
 */
public class QuestionResultActivity extends BaseActivity implements View.OnClickListener {
    private final static String GREETING = ",您好";
    private String call = "先生";
    private TextView mName;
    private TextView mSalt;
    private TextView mBlood;
    private TextView mScore;
    private Button mPrint;
    private Button mBack;
    private LinearLayout mHealthTip;
    private ScrollView mScrollerView;
    private static Context context;
    private TextView tv_tips1;
    private TextView tv_tips2;
    private TextView tv_tips3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_result);
        init();
        context = this;
    }

    private void init() {
        mScrollerView = (ScrollView) findViewById(R.id.result_scrollView);
        mName = (TextView) findViewById(R.id.result_name);
        mBlood = (TextView) findViewById(R.id.result_blood);
        mSalt = (TextView) findViewById(R.id.result_salt);
        mScore = (TextView) findViewById(R.id.result_score);
        mPrint = (Button) findViewById(R.id.result_print);
        mBack = (Button) findViewById(R.id.result_back);
        tv_tips1 = (TextView) findViewById(R.id.tv_tips1);
        tv_tips2 = (TextView) findViewById(R.id.tv_tips2);
        tv_tips3 = (TextView) findViewById(R.id.tv_tips3);
        mHealthTip = (LinearLayout) findViewById(R.id.question_health_tip);
        mPrint.setOnClickListener(this);
        mBack.setOnClickListener(this);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String time = format.format(date);
        Intent resultIntent = getIntent();
        Result result = (Result) resultIntent.getSerializableExtra("result");
        String name = result.getName();
        String sex = result.getSex();
        String blood = result.getBlood();
        String salt = result.getSalt();
        String score = result.getScore();
        String healthTip = result.getHealthTip();
        if (sex.equals("1")) {
            call = "女士";
        }
        mName.setText(name + call + GREETING);
        String textStr = "您的血压值为: <font color=\"#178078\">" + blood + "</font>";
        String textStr1 = "您的综合得分为: <font color=\"#178078\">" + score + "</font>";
        mBlood.setText(Html.fromHtml(textStr));
        mSalt.setText(salt);
        mScore.setText(Html.fromHtml(textStr1));
        String[] tipArr = healthTip.split("-");
        tv_tips1.setText(tipArr.length > 0 ? tipArr[0].trim() : "");
        tv_tips2.setText(tipArr.length > 1 ? tipArr[1].trim() : "");
        tv_tips3.setText(tipArr.length > 2 ? tipArr[2].trim() : "");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.result_print:
                mPrint.setVisibility(View.GONE);
                mBack.setVisibility(View.GONE);
                Bitmap bitmap = getBitmap(mScrollerView);
                if (null != bitmap) {
                    AidlUtil.getInstance().printBitmap(bitmap);
                    bitmap.recycle();
                    bitmap = null;
                } else {
                    ToastUtils.show(this, "发生未知错误，请稍后重试");
                    mPrint.setText("打印");
                }
                mPrint.setVisibility(View.VISIBLE);
                mBack.setVisibility(View.VISIBLE);
                finishActivity();
                //打印
                break;
            case R.id.result_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mHealthTip.removeAllViews();
        super.onDestroy();
    }

    /**
     * layout 转 bitmap
     */
    public Bitmap getBitmapByView(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
        }
        bitmap = Bitmap.createBitmap(scrollView.getWidth() - ConvertUtils.dip2px(QuestionResultActivity.this, 40), h,
            Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }

    public static Bitmap getBitmap(ScrollView scrollView) {
        int viewWidth = scrollView.getWidth();
        int viewHeight = 0;
        // 获取scrollview实际高度
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            viewHeight += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
        }
        // 创建对应大小的bitmap
        Bitmap bitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }
}
