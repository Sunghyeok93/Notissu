package com.notissu.UI.NoticeList.Search;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.notissu.UI.NoticeList.NoticeListContract;

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
    }

    @Override
    public void start() {
        String query = bundle.getString(NoticeListContract.KEY_SEARCH_QUERY);
        view.showTitle(query);

        view.showSearch(query);
    }
}
