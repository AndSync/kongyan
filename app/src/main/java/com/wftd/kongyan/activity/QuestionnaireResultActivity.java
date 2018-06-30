package com.wftd.kongyan.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.wftd.kongyan.R;
import com.wftd.kongyan.base.BaseActivity;
import com.wftd.kongyan.bean.Result;
import com.wftd.kongyan.utils.AidlUtil;
import com.wftd.kongyan.utils.ConvertUtils;
import com.wftd.kongyan.utils.ToastUtils;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 调查结果
 */
public class QuestionnaireResultActivity extends BaseActivity implements View.OnClickListener {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionnaire_dialog);
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
        String tips[] = healthTip.split("-");
        for (int i = 0; i < tips.length; i++) {
            switch (i) {
                case 0:
                    TextView t = new TextView(this);
                    t.setText("根据评估，您的饮食习惯属于：");
                    t.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
                    t.setHeight(ConvertUtils.dip2px(this, 20));
                    t.setGravity(Gravity.CENTER_VERTICAL);
                    t.setTextSize(16);
                    t.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    t.setTextColor(getResources().getColor(R.color.text_black));
                    mHealthTip.addView(t);
                    break;
                case 1:
                    TextView t1 = new TextView(this);
                    t1.setText("您目前的血压水平：");
                    t1.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
                    t1.setHeight(ConvertUtils.dip2px(this, 20));
                    t1.setGravity(Gravity.CENTER_VERTICAL);
                    t1.setTextSize(16);
                    t1.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    t1.setTextColor(getResources().getColor(R.color.text_black));
                    mHealthTip.addView(t1);
                    break;
                case 2:
                    TextView t2 = new TextView(this);
                    t2.setText("建议：");
                    t2.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
                    t2.setHeight(ConvertUtils.dip2px(this, 20));
                    t2.setGravity(Gravity.CENTER_VERTICAL);
                    t2.setTextSize(16);
                    t2.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    t2.setTextColor(getResources().getColor(R.color.text_black));
                    mHealthTip.addView(t2);
                    break;
                default:
                    break;
            }
            TextView t = new TextView(this);
            t.setText(tips[i]);
            t.setWidth(ConvertUtils.dip2px(this, 200));
            t.setHeight(ConvertUtils.dip2px(this, 60));
            t.setGravity(Gravity.CENTER_VERTICAL);
            if (tips[i].length() < 15) {
                t.setTextSize(16);
            } else {
                t.setTextSize(16);
            }
            t.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            t.setTextColor(getResources().getColor(R.color.green));
            mHealthTip.addView(t);
            if (i == tips.length) {
                TextView v = new TextView(this);
                v.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
                v.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
                mHealthTip.addView(v);
            }
        }

        //        for (String tip : tips) {
        //            TextView t = new TextView(this);
        //            t.setText(tip);
        //            t.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        //            t.setHeight(ConvertUtils.dip2px(this, 40));
        //            t.setGravity(Gravity.CENTER_VERTICAL);
        //            t.setTextSize(16);
        //            t.setTextColor(getResources().getColor(R.color.black));
        //            mHealthTip.addView(t);
        //        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.result_print:
                mPrint.setVisibility(View.GONE);
                mBack.setVisibility(View.GONE);
                //Bitmap bitmap=null;
                //saveLongImageFile(mScrollerView);
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

                QuestionnaireResultActivity.this.startActivity(
                    new Intent(QuestionnaireResultActivity.this, ScreenActivity.class));
                //打印
                break;
            case R.id.result_back:
                finish();
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
        bitmap =
            Bitmap.createBitmap(scrollView.getWidth() - ConvertUtils.dip2px(QuestionnaireResultActivity.this, 40), h,
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

    public static String saveLongImageFile(ScrollView scrollView) {
        String filePath = "";

        try {
            int viewWidth = scrollView.getWidth();
            int viewHeight = 0;
            // 获取scrollview实际高度
            for (int i = 0; i < scrollView.getChildCount(); i++) {
                viewHeight += scrollView.getChildAt(i).getHeight();
                scrollView.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
            }

            // 创建对应大小的bitmap
            Bitmap bitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.RGB_565);
            final Canvas canvas = new Canvas(bitmap);
            scrollView.draw(canvas);
            if (bitmap != null) {
                filePath = context.getExternalCacheDir().getPath();
                Log.d("aaaaaa", filePath);
                saveBitmapToSdCard(filePath, bitmap);
                bitmap.recycle();
            }
        } catch (OutOfMemoryError e) {
            System.gc();
            return "";
        }
        if (TextUtils.isEmpty(filePath)) {
            return "";
        }
        return filePath;
    }

    public static void saveBitmapToSdCard(String path, Bitmap bitmap) {
        if (bitmap == null || TextUtils.isEmpty(path)) {
            return;
        }
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(path));
            // 采用压缩转档方法
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            // 调用flush()方法，更新BufferStream
            bos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }
    }
}
