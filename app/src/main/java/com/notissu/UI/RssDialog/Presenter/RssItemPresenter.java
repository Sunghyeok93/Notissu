package com.notissu.UI.RssDialog.Presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.notissu.Model.RssItem;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.notissu.UI.RssDialog.View.RssItemDialog.KEY_RSSITEM;

/**
 * Created by forhack on 2017-01-01.
 */

public class RssItemPresenter implements RssItemContract.Presenter {
    private RssItemContract.View view;
    private Bundle bundle;
    private RssItem rssitem;

    public RssItemPresenter(Bundle bundle, @NonNull RssItemContract.View view) {
        this.bundle = bundle;
        this.view = checkNotNull(view);

        view.setPresenter(this);
    }

    @Override
    public void start() {
        rssitem = bundle.getParcelable(KEY_RSSITEM);

        view.showTitle(rssitem.getTitle());
        view.showTime(rssitem.getPublishDateLong());
    }

    @Override
    public void addBrowser() {
        view.showBrowser(rssitem.getTitle(), rssitem.getLink());
    }
}
