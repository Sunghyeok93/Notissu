package com.notissu.NoticeTab.Presenter;

import com.notissu.BasePresenter;
import com.notissu.BaseView;

/**
 * Created by forhack on 2017-01-01.
 */

public interface NoticeTabContract {
    interface View extends BaseView<Presenter> {

        void addTabs();

        int getTabCount();
    }

    interface Presenter extends BasePresenter {

    }

}
