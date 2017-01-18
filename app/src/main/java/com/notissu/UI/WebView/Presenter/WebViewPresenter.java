package com.notissu.UI.WebView.Presenter;

import android.content.Intent;
import android.os.Bundle;

import com.notissu.UI.NoticeList.View.NoticeListFragment;

/**
 * Created by forhack on 2017-01-17.
 */

public class WebViewPresenter implements WebViewContract.Presenter {
    private WebViewContract.View mView;
    private Bundle mBundle;

    public WebViewPresenter(Bundle bundle, WebViewContract.View view) {
        this.mView = view;
        this.mBundle = bundle;
        view.setPresenter(this);
    }

    @Override
    public void start() {
        String title = mBundle.getString(NoticeListFragment.KEY_RSS_TITLE);
        mView.setTitle(title);

        String link = mBundle.getString(NoticeListFragment.KEY_RSS_LINK);
        mView.showWebView(link);

    }
}