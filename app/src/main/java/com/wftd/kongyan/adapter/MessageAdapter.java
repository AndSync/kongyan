package com.wftd.kongyan.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wftd.kongyan.R;
import com.wftd.kongyan.base.BaseAdapter;
import com.wftd.kongyan.entity.Message;
import com.wftd.kongyan.entity.Question;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by liwei on 2018/6/15.
 */

public class MessageAdapter extends BaseAdapter<Question> {

    private LayoutInflater inflater;
    DecimalFormat textformat = null;
    private boolean isUp = false;

    public boolean isUp() {
        return isUp;
    }


    public MessageAdapter(Activity context, List<Message> messages) {
        this.mContext = context;
        this.mDataList = messages;
        inflater = LayoutInflater.from(context);
    }

    public MessageAdapter(Activity context) {
        this.mContext = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.message_item, viewGroup, false);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.context = (TextView) convertView.findViewById(R.id.context);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Message message = (Message) mDataList.get(i);
        holder.title.setText(message.getTitle());
        holder.context.setText(message.getContent());

        return convertView;
    }


    class ViewHolder {
        private TextView title, context;
    }


    public static interface OnQuestion {
        // true add; false cancel
        public void onItemClick(Question question); //传递boolean类型数据给activity
    }

    // add click callback
    OnQuestion onItemAddClick;

    public void setOnAddClickListener(OnQuestion onItemAddClick) {
        this.onItemAddClick = onItemAddClick;
    }


}



