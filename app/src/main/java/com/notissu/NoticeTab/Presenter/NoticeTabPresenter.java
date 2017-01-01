package com.notissu.NoticeTab.Presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.notissu.NoticeTab.Adapter.NoticeTabPagerAdapterContract;
import com.notissu.NoticeTab.View.NoticeTabFragment;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by forhack on 2017-01-01.
 */

public class NoticeTabPresenter implements NoticeTabContract.Presenter {
    private NoticeTabContract.View view;

    private Bundle bundle;

    private NoticeTabPagerAdapterContract.Model adapterModel;
    private NoticeTabPagerAdapterContract.View adapterView;

    public NoticeTabPresenter(Bundle arguments,
                              @NonNull NoticeTabContract.View view,
                              @NonNull NoticeTabPagerAdapterContract.Model adapterModel,
                              @NonNull NoticeTabPagerAdapterContract.View adapterView) {
        this.bundle = arguments;
        this.view = checkNotNull(view);
        this.adapterModel = adapterModel;
        this.adapterView = adapterView;

        view.setPresenter(this);
    }

    @Override
    public void start() {
        //타이틀 변경
        String title = bundle.getString(NoticeTabFragment.KEY_TITLE);
        int flag = bundle.getInt(NoticeTabFragment.KEY_FLAG);

        view.addTabs();

        adapterModel.setData(title,flag, view.getTabCount());
    }
}
