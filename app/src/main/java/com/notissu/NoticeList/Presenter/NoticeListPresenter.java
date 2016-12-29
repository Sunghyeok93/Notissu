package com.notissu.NoticeList.Presenter;

import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by forhack on 2016-12-29.
 */

public class NoticeListPresenter implements NoticeListContract.Presenter{
    private NoticeListContract.View view;

    public NoticeListPresenter(@NonNull NoticeListContract.View view) {
        this.view = checkNotNull(view,"NoticeListContract.View cannot be null");
        view.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
