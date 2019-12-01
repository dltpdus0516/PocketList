package com.cookandroid.pocketlist;

import android.view.View;

public interface OnListItemClickListener {
    public void onItemClick(ListAdapter.ViewHolder holder, View view, int position);
}
