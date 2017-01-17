package com.notissu.UI.Search.Presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.notissu.UI.Main.View.MainActivity;
import com.notissu.UI.NoticeList.View.NoticeListFragment;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by forhack on 2017-01-01.
 */

public class SearchPresenter implements SearchContract.Presenter {
    private SearchContract.View view;
    private Bundle bundle;

    public SearchPresenter(Bundle bundle, @NonNull SearchContract.View view) {
        this.bundle = bundle;
        this.view = checkNotNull(view);

        view.setPresenter(this);
    }

    @Override
    public void start() {
        String query = bundle.getString(NoticeListFragment.KEY_SEARCH_QUERY);
        view.showTitle(query);

        Fragment fragment = NoticeListFragment.newInstance(MainActivity.FLAG_SEARCH, query);
        view.showSearch(fragment);

    }
}
