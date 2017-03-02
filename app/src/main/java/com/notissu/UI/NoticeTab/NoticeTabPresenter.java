package com.notissu.UI.NoticeTab;

import android.os.Bundle;
import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by forhack on 2017-01-01.
 */

public class NoticeTabPresenter implements NoticeTabContract.Presenter {
    private NoticeTabContract.View mView;

    private Bundle mBundle;

    private NoticeTabPagerAdapterContract.Model mAdapterModel;
    private NoticeTabPagerAdapterContract.View mAdapterView;

    public NoticeTabPresenter(Bundle arguments,
                              @NonNull NoticeTabContract.View view) {
        this.mBundle = arguments;
        this.mView = checkNotNull(view);

        view.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void setAdapter(NoticeTabPagerAdapter noticeTabPagerAdapter) {
        mAdapterModel = noticeTabPagerAdapter;
        mAdapterView = noticeTabPagerAdapter;
        mView.setAdapter(noticeTabPagerAdapter);
    }

    @Override
    public void setTab(String[] noticeCategory) {
        String title = mBundle.getString(NoticeTabContract.KEY_TITLE);
        int flag = mBundle.getInt(NoticeTabContract.KEY_FLAG);
        mView.addTabs(noticeCategory);
        mAdapterModel.setData(title, flag, noticeCategory.length);
        mAdapterView.refresh();
    }
}
