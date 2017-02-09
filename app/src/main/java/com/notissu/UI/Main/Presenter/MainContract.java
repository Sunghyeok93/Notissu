package com.notissu.UI.Main.Presenter;

import android.support.v4.app.Fragment;

import com.notissu.BasePresenter;
import com.notissu.BaseView;
import com.notissu.Model.Keyword;

/**
 * Created by forhack on 2017-01-01.
 */

public interface MainContract {
    int FLAG_MAIN_NOTICE = 0;
    int FLAG_LIBRARY_NOTICE = 1;
    int FLAG_STARRED = 2;
    int FLAG_KEYWORD = 3;
    int FLAG_SEARCH = 4;
    int FLAG_SETTING = 5;

    interface View extends BaseView<Presenter> {

        void showNavigation();

        void addMenuKeyword(Keyword keyword);

        void showFragment(int presentFegment, Fragment fragment);

        void showAddKeyword();
    }

    interface Presenter extends BasePresenter {

        void onFabClick();

        void onAddNewItem(Keyword keyword);
    }
}
