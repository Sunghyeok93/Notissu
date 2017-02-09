package com.notissu.UI.Main.AddKeywordDialog.Presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

public class AddKeywordPresenter implements AddKeywordContract.Presenter {
    private AddKeywordContract.View mView;


    public AddKeywordPresenter(@NonNull AddKeywordContract.View view) {
        this.mView = checkNotNull(view);

        view.setPresenter(this);
    }

    @Override
    public void start() {

    }

}
