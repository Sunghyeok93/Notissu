package com.notissu.UI.NoticeList.Presenter;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.notissu.BasePresenter;
import com.notissu.BaseView;
import com.notissu.Model.Notice;
import com.notissu.UI.NoticeList.Adapter.NoticeListAdapter;

/**
 * Created by forhack on 2016-12-29.
 */

public interface NoticeListContract {
    interface View extends BaseView<Presenter> {
        void showTitle(String title);

        void showProgress();

        void hideProgress();

        void showSearch(String query);

        void showOptionMenu(Menu menu, MenuInflater inflater);

        void hideRefreshing();

        void showNotice(int noticeId);

        void setAdapter(NoticeListAdapter noticeListAdapter);
    }

    interface Presenter extends BasePresenter {

        void onItemClick(android.view.View itemView, int position);

        void addSearch(String query);

        void loadOptionMenu(Menu menu, MenuInflater inflater);

        void readAllItem();

        void setAdapter(NoticeListAdapter noticeListAdapter);

        void fetchNoticeList();

        void onStarredClick(android.view.View view, Notice notice);
    }

    interface OnFetchNoticeListListener {
        void onFetchNoticeList(String response);
    }


}
