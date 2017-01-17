package com.notissu.UI.Search.Presenter;

import android.support.v4.app.Fragment;

import com.notissu.BasePresenter;
import com.notissu.BaseView;

/**
 * Created by forhack on 2017-01-01.
 */

public interface SearchContract {
    interface View extends BaseView<Presenter> {

        void showTitle(String query);

        void showSearch(Fragment fragment);
    }

    interface Presenter extends BasePresenter {

    }
}
