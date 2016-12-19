package com.notissu.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.notissu.Database.StarredProvider;
import com.notissu.Database.StarredProviderImp;
import com.notissu.Model.RssItem;
import com.notissu.R;
import com.notissu.Database.RssDatabase;
import com.notissu.Util.LogUtils;

import java.util.ArrayList;

/**
 * Created by forhack on 2016-10-18.
 */

public class NoticeAdapter extends ArrayAdapter<RssItem> {
    private static final String TAG = LogUtils.makeLogTag(NoticeAdapter.class);
    Context context;
    ViewHolder viewHolder;
    //ListView에 보여줄 Item
    ArrayList<RssItem> noticeList = new ArrayList<>();
    //즐겨찾기에 추가된 List
    ArrayList<RssItem> starredList = new ArrayList<>();
    //노드들 CheckBox 체크되어있는지 저장하기.
    boolean[] isChecked;


    public NoticeAdapter(Context context, ArrayList<RssItem> noticeList, ArrayList<RssItem> starredList) {
        super(context, android.R.layout.simple_expandable_list_item_2);
        this.context = context;
        this.noticeList = noticeList;
        this.starredList = starredList;
        isChecked = new boolean[this.noticeList.size()];

        //보여줄 노드 List 중 즐겨찾기에 등록된 Row를 찾아서 isChecked를 Check하기
        //즐겨찾기한 리스트가 없을 수 있으니 성능을 위해 for문 바깥으로 넣음.
        for (RssItem starredItem : starredList) {
            String starredTitle = starredItem.getTitle();
            for (int i = 0; i < noticeList.size(); i++) {
                String noticeTitle = noticeList.get(i).getTitle();
                if (noticeTitle.equals(starredTitle)) { //같다면!
                    isChecked[i] = true;
                    break;
                }
            }
        }

    }

    @Override
    public int getCount() {
        return noticeList.size();
    }

    @Override
    public RssItem getItem(int i) {
        return noticeList.get(i);
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
            viewHolder.llWrapper = (LinearLayout) view.findViewById(R.id.notice_ll_cb_wrapper);
            viewHolder.cbStar = (CheckBox) view.findViewById(R.id.notice_cb_star);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        final int index = i;
        final String title = getItem(index).getTitle();

        viewHolder.tvSubject.setText(title);
        if (getItem(index).getIsRead() == RssItem.READ) {
            viewHolder.tvSubject.setTextColor(Color.parseColor("#aaaaaa"));
            viewHolder.tvSubject.setTypeface(Typeface.DEFAULT);
        } else if (getItem(index).getIsRead() == RssItem.NOT_READ) {
            viewHolder.tvSubject.setTextColor(Color.parseColor("#000000"));
            viewHolder.tvSubject.setTypeface(Typeface.DEFAULT_BOLD);
        }
        viewHolder.tvTime.setText(getItem(index).getPublishDateShort());
        viewHolder.cbStar.setChecked(isChecked[index]);
        viewHolder.llWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StarredProvider starredProvider = new StarredProviderImp();
                CheckBox cb = (CheckBox)v.findViewById(R.id.notice_cb_star);

                //클릭되고 난 다음이라 isChecked는 체크되는 순간이다.
                if (cb.isChecked()) {
                    cb.setChecked(false);
                    starredProvider.deleteStarred(title);
                    isChecked[index] = false;
                } else {
                    cb.setChecked(true);
                    starredProvider.addStarred(title);
                    isChecked[index] = true;

                }
            }
        });
        return view;
    }

    static class ViewHolder{
        TextView tvSubject;
        TextView tvTime;
        CheckBox cbStar;
        LinearLayout llWrapper;
    }

}
