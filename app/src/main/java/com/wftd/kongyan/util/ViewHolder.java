package com.wftd.kongyan.util;

import android.util.SparseArray;
import android.view.View;

/**
 * @author AndSync
 * @date 2017/11/13
 * Copyright © 2014-2017 北京智阅网络科技有限公司 All rights reserved.
 */
public class ViewHolder {
    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }
}

