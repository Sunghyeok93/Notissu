package com.notissu.UI.AddKeywordDialog.Presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.notissu.UI.AddKeywordDialog.View.AddKeywordDialog.KEY_KEYWORD;

/**
 * Created by forhack on 2017-01-01.
 */

public class AddKeywordPresenter implements AddKeywordContract.Presenter {
    private AddKeywordContract.View view;



    public AddKeywordPresenter(@NonNull AddKeywordContract.View view) {
        this.view = checkNotNull(view);

        view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void addKeyword(String keyword) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_KEYWORD, keyword);
        view.enableAdd(bundle);

        view.close();

    }
}
