package com.notissu.NoticeList.Presenter;

import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.notissu.BasePresenter;
import com.notissu.BaseView;
import com.notissu.Model.RssItem;

import java.util.ArrayList;

/**
 * Created by forhack on 2016-12-29.
 */

public interface NoticeListContract {
    interface View extends BaseView<Presenter> {
        void showTitle(String title);

        void showProgress();

        void hideProgress();

        void showRssDialog(RssItem rssitem);

        void showSearch(String query);

        void showOptionMenu(Menu menu, MenuInflater inflater);

        void hideRefreshing();
    }

    interface Presenter extends BasePresenter {

        boolean isMain();
        boolean isLibrary();
        boolean isStarred();
        boolean isKeyword();
        boolean isSearch();

        void onItemClick(android.view.View itemView, int position);

        void addSearch(String query);

        void loadOptionMenu(Menu menu, MenuInflater inflater);

        void readAllItem();

        void fetchNotice();

        void refreshList();
    }

}
