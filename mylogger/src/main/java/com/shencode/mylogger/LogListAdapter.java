package com.shencode.mylogger;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class LogListAdapter extends BaseAdapter {

    private List<LogInfo> items;

    public LogListAdapter(List<LogInfo> items){
        this.items=items;
    }

    @Override
    public int getCount() {
        if(items==null)return 0;
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        if(items==null)return null;
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        LogInfo item=items.get(position);
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Log.d("LogListAdapter",convertView+"|"+parent);
        LogInfo item=items.get(position);
        TextView mTextView = new TextView(parent.getContext());
        mTextView.setText(item.toString());
        mTextView.setTextSize(15);
        int p=item.getPriority();
        if(p==2){
            mTextView.setTextColor(Color.BLACK);
        }
        else if(p==3){
            mTextView.setTextColor(Color.BLUE);
        }
        else if(p==4){
            mTextView.setTextColor(0xFF008000);// 绿色
        }
        else if(p==5){
            mTextView.setTextColor(0xFFFF6100);//橙色
        }else if(p==6){
            mTextView.setTextColor(Color.RED);
        }
        else{
            mTextView.setTextColor(Color.GRAY);
        }
        return mTextView;
    }
}
