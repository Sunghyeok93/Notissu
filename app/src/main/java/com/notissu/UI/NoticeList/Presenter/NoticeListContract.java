package com.notissu.UI.NoticeList.Presenter;

import android.view.Menu;
import android.view.MenuInflater;

import com.notissu.BasePresenter;
import com.notissu.BaseView;
import com.notissu.Model.RssItem;

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

        void showNotice(String title, String link);
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
