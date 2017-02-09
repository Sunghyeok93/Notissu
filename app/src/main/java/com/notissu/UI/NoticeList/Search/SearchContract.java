package com.notissu.UI.NoticeList.Search;

import android.support.v4.app.Fragment;

import com.notissu.BasePresenter;
import com.notissu.BaseView;

/**
 * Created by forhack on 2017-01-01.
 */

public interface SearchContract {
    interface View extends BaseView<Presenter> {

        void showTitle(String query);

        void showSearch(String query);
    }

    interface Presenter extends BasePresenter {

    }
}
