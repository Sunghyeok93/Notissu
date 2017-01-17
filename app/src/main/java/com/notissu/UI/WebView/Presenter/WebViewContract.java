package com.notissu.UI.WebView.Presenter;

import com.notissu.BasePresenter;
import com.notissu.BaseView;

/**
 * Created by forhack on 2017-01-17.
 */

public interface WebViewContract {
    interface View extends BaseView<Presenter> {

        void showWebView(String link);

        void setTitle(String title);
    }

    interface Presenter extends BasePresenter {

    }

}
