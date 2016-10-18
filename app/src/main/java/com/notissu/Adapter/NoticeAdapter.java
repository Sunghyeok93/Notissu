package com.notissu.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.notissu.R;

import java.util.ArrayList;

/**
 * Created by forhack on 2016-10-18.
 */

public class NoticeAdapter extends ArrayAdapter<String> {
    Context context;
    ViewHolder viewHolder;
    ArrayList<String> noticeSubject = new ArrayList<>();

    public NoticeAdapter(Context context, ArrayList<String> noticeSubject) {
        super(context, android.R.layout.simple_expandable_list_item_2);
        this.context = context;
        this.noticeSubject = noticeSubject;
    }

    public NoticeAdapter(Context context) {
        super(context, android.R.layout.simple_expandable_list_item_2);
        this.context = context;
    }




    @Override
    public int getCount() {
        return noticeSubject.size();
    }

    @Override
    public String getItem(int i) {
        return noticeSubject.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = View.inflate(context, R.layout.content_notice_list, null);
            viewHolder = new ViewHolder();
            viewHolder.tvSubject = (TextView) view.findViewById(R.id.notice_tv_subject);
            viewHolder.cbStar = (CheckBox) view.findViewById(R.id.notice_cb_star);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvSubject.setText(getItem(i));
        viewHolder.cbStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        return view;
    }

    public void add(String str) {
        noticeSubject.add(str);
    }

    static class ViewHolder{
        TextView tvSubject;
        CheckBox cbStar;
    }
}
