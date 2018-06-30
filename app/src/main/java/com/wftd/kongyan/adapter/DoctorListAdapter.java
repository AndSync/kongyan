package com.wftd.kongyan.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.wftd.kongyan.R;
import com.wftd.kongyan.base.BaseAdapter;
import com.wftd.kongyan.entity.Doctor;
import com.wftd.kongyan.util.ViewHolder;
import java.util.List;

/**
 * @author AndSync
 * @date 2018/6/29
 * Copyright © 2014-2018 AndSync All rights reserved.
 */
public class DoctorListAdapter extends BaseAdapter {

    private Context context;
    private List<Doctor> list;

    public DoctorListAdapter(Context context, List<Doctor> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_doctor, null);
        }
        TextView tvTitle = ViewHolder.get(convertView, R.id.tv_name);
        Doctor entity = list.get(position);

        tvTitle.setText(entity.getName());
        return convertView;
    }
}
