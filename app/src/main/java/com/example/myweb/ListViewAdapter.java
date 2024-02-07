package com.example.myweb;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebBackForwardList;
import android.webkit.WebHistoryItem;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {

    //The list of messages to be displayed
    private WebBackForwardList itemList;
    //An object that can create/inflate views using the context/activity provided
    private LayoutInflater inflater;
    public ListViewAdapter(Activity context, WebBackForwardList itemList) {
        super();
        Log.d("HistoryTag",""+itemList.getSize());
        this.itemList = itemList;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //Abstract methods used by parent
    public int getCount(){ return itemList.getSize();}
    public Object getItem(int position) {return itemList.getItemAtIndex (position);}
    public long getItemId(int position) {return position; }

    // Convenient store to be used with tagging
    public static class ViewHolder {
        TextView textViewTitle;
        TextView textView;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView==null) {
//No previous view, instantiate holder, inflate view and setup views of holder
            holder = new ViewHolder();
            convertView = inflater. inflate(R.layout.history_list, null);

            holder.textViewTitle = (TextView) convertView. findViewById(R.id.textViewTitle);
            holder.textView = (TextView) convertView.findViewById(R.id.textView);

//Remember the holder inside the view
            convertView.setTag(holder);
        }
        else
//Get holder to prepare for update with new data
            holder=(ViewHolder)convertView.getTag();

//Update views
        WebHistoryItem historyItem = (WebHistoryItem) itemList.getItemAtIndex(position);
        holder.textViewTitle.setText(historyItem.getTitle());
        holder.textView.setText(historyItem.getUrl());

        return convertView;
    }
}