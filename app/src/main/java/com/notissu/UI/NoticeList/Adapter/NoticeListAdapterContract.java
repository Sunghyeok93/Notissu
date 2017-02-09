package com.notissu.UI.NoticeList.Adapter;

import android.view.View;

import com.notissu.Model.Notice;
import com.notissu.Model.RssItem;
import com.notissu.View.Interface.OnRecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by forhack on 2016-12-29.
 */

public interface NoticeListAdapterContract {
    interface View {
        void refresh();

        void setItemRead(android.view.View itemView);
    }

    interface Model {
        void setOnRecyclerItemClickListener(OnRecyclerItemClickListener onRecyclerItemClickListener);

        int getCount();

        Notice getItem(int i);

        void setLists(List<Notice> noticeList);

        void setOnStarredClickListener(OnStarredClickListner onStarredClickListener);
    }

    interface OnStarredClickListner {
        void onClick(android.view.View view, Notice notice);
    }
}
