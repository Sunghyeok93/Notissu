package com.notissu.UI.RssDialog.Presenter;

import com.notissu.BasePresenter;
import com.notissu.BaseView;

/**
 * Created by forhack on 2017-01-01.
 */

public interface RssItemContract {
    interface View extends BaseView<Presenter> {

        void showTitle(String title);

        void showTime(String publishDateLong);

        void showBrowser(String link);
    }

    interface Presenter extends BasePresenter {
        void addBrowser();
    }
}
