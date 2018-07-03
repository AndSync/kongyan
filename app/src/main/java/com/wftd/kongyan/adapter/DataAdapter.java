package com.wftd.kongyan.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.wftd.kongyan.R;
import com.wftd.kongyan.activity.QuestionResultActivity;
import com.wftd.kongyan.base.BaseAdapter;
import com.wftd.kongyan.entity.Question;
import com.wftd.kongyan.entity.Result;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by liwei on 2018/6/15.
 */

public class DataAdapter extends BaseAdapter<Question> {

    private LayoutInflater inflater;
    private boolean isUp = false;

    public boolean isUp() {
        return isUp;
    }

    public void setUp(boolean up) {
        isUp = up;
    }

    public DataAdapter(Activity context, List<Question> questions) {
        this.mContext = context;
        this.mDataList = questions;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_data_manager, viewGroup, false);
            holder = new ViewHolder();
            holder.print = (ImageView) convertView.findViewById(R.id.print);
            holder.upfile = (ImageView) convertView.findViewById(R.id.upfile);
            holder.number = (TextView) convertView.findViewById(R.id.data);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Question question = (Question) mDataList.get(i);
        if (question.isUpdate()) {
            holder.upfile.setVisibility(View.GONE);
        } else {
            holder.upfile.setVisibility(View.VISIBLE);
        }
        SimpleDateFormat oldDf = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat newDf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date date = oldDf.parse(question.getSubmitDate());
            holder.number.setText(newDf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.upfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemAddClick.onItemClick(question);
            }
        });

        holder.print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int index = question.getSaltThreshold();

                int mNumber = getValues(question);
                if (index == 1) {
                    if (mNumber <= 18) {
                        mNumber = 19;
                    }
                }
                Result result1 = Result.getRelult(question, mNumber);

                //if (mNumber < 9) {
                //    result1 = new Result(question.getName(), question.isSex() == 0 ? "先生" : "女士",
                //        question.getSystolicPressure() + "/" + question.getDiastolicPressure() + " mmHg", "30%",
                //        mNumber + "", "低盐（食盐摄入量合适）- 处于正常范围 - 保持清淡饮食，合理膳食");
                //} else if (9 <= mNumber && mNumber <= 13) {
                //    result1 = new Result(question.getName(), question.isSex() == 0 ? "先生" : "女士",
                //        question.getSystolicPressure() + "/" + question.getDiastolicPressure() + " mmHg", "30%",
                //        mNumber + "", "正d常（食盐摄入量合适）- 处于正常范围 - 请保持清淡饮食，建议咨询门诊医生是否需要调整降压治疗");
                //} else if (14 <= mNumber && mNumber <= 19) {
                //    result1 = new Result(question.getName(), question.isSex() == 0 ? "先生" : "女士",
                //        question.getSystolicPressure() + "/" + question.getDiastolicPressure() + " mmHg", "30%",
                //        mNumber + "", "中盐（食盐摄入量偏高）- 超出正常范围偏高 - 请咨询门诊医生是否需要调整您的饮食习惯，建议您定期测量血压");
                //} else if (20 <= mNumber) {
                //    result1 = new Result(question.getName(), question.isSex() == 0 ? "先生" : "女士",
                //        question.getSystolicPressure() + "/" + question.getDiastolicPressure() + " mmHg", "30%",
                //        mNumber + "", "高盐（食盐摄入量偏高）- 超出正常范围偏高 - 请咨询门诊医生是否需要调整您的饮食习惯，您的血压水平是否合适，以获得更恰当的治疗");
                //}
                Intent intent = new Intent(mContext, QuestionResultActivity.class);
                intent.putExtra("result", result1);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        private ImageView print, upfile;
        private TextView number;
    }

    public interface OnQuestion {
        void onItemClick(Question question);
    }

    public OnQuestion onItemAddClick;

    public void setOnAddClickListener(OnQuestion onItemAddClick) {
        this.onItemAddClick = onItemAddClick;
    }

    public int getValues(Question question) {
        return question.getQ1()
            + question.getQ2()
            + question.getQ3()
            + question.getQ4()
            + question.getQ5()
            + question.getQ6()
            + question.getQ7()
            + question.getQ8()
            + question.getQ9()
            + question.getQ10()
            + question.getQ11()
            + question.getQ12()
            + question.getQ13();
    }
}



