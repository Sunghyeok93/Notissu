package com.notissu.NoticeList.Adapter;

import com.notissu.Model.RssItem;
import com.notissu.View.Interface.OnRecyclerItemClickListener;

import java.util.ArrayList;

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

        void setLists(ArrayList<RssItem> noticeList, ArrayList<RssItem> starredList);

        int getCount();

        RssItem getItem(int i);

        void remove(RssItem item);

        void addItems(ArrayList<RssItem> items);

        void removeAll();
    }

}
