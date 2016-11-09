package com.notissu.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.notissu.Model.NoticeRow;
import com.notissu.R;

import java.util.ArrayList;

/**
 * Created by forhack on 2016-10-18.
 */

public class NoticeAdapter extends ArrayAdapter<NoticeRow> {
    Context context;
    ViewHolder viewHolder;
    ArrayList<NoticeRow> noticeRows = new ArrayList<>();

    public NoticeAdapter(Context context, ArrayList<NoticeRow> noticeRows) {
        super(context, android.R.layout.simple_expandable_list_item_2);
        this.context = context;
        this.noticeRows = noticeRows;
    }

    public NoticeAdapter(Context context) {
        super(context, android.R.layout.simple_expandable_list_item_2);
        this.context = context;
    }

    @Override
    public int getCount() {
        return noticeRows.size();
    }

    @Override
    public NoticeRow getItem(int i) {
        return noticeRows.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = View.inflate(context, R.layout.row_notice_list, null);
            viewHolder = new ViewHolder();
            viewHolder.tvSubject = (TextView) view.findViewById(R.id.notice_tv_subject);
            viewHolder.tvTime = (TextView) view.findViewById(R.id.notice_tv_time);
            viewHolder.cbStar = (CheckBox) view.findViewById(R.id.notice_cb_star);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tvSubject.setText(getItem(i).getTitle());
        viewHolder.tvTime.setText(getItem(i).getTime());
        viewHolder.cbStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        return view;
    }

    public void add(NoticeRow noticeRow) {
        noticeRows.add(noticeRow);
    }

    static class ViewHolder{
        TextView tvSubject;
        TextView tvTime;
        CheckBox cbStar;
    }

}
