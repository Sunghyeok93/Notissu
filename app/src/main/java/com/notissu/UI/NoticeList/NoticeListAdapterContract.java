package com.notissu.UI.NoticeList;

import com.notissu.Model.Notice;
import com.notissu.View.Interface.OnRecyclerItemClickListener;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmModel;

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

        void setOnStarredClickListener(OnStarredClickListner onStarredClickListener);

        void readAll();

        List<Notice> getList();

        void addList(List<Notice> noticeList);
    }

    interface OnStarredClickListner {
        void onClick(android.view.View view, Notice notice);
    }
}
