package com.notissu.UI.Main.AddKeywordDialog;

import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

public class AddKeywordPresenter implements AddKeywordContract.Presenter {
    private AddKeywordContract.View mView;

    public AddKeywordPresenter(@NonNull AddKeywordContract.View view) {
        this.mView = checkNotNull(view);
    }
}
