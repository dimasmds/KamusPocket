package com.riotfallen.kamuspocket.adapter;

import android.view.View;

public class CustomOnItemClickListener implements View.OnClickListener {

    private int position;
    private OnItemClickCallback onItemClickCallBack;

    CustomOnItemClickListener(int position, OnItemClickCallback onITemClickCallBack) {
        this.position = position;
        this.onItemClickCallBack = onITemClickCallBack;
    }

    @Override
    public void onClick(View view) {
        onItemClickCallBack.onItemClicked(view, position);
    }


    public interface OnItemClickCallback {
        void onItemClicked(View view, int position);
    }
}
