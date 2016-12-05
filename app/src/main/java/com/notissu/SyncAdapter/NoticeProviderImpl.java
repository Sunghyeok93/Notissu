package com.notissu.SyncAdapter;

import android.content.Context;

import com.notissu.Model.RssItem;

import java.util.List;

/**
 * Created by forhack on 2016-12-04.
 */

/*필요할 때 DB연결해서 바로 전달해줘도 되는데 이렇게 한 이유는
* 그냥 순수하게 분리, 그 이유 밖에 없는것 같다.*/

public class NoticeProviderImpl implements NoticeProvider {
    RssDatabase mRssDatabase;

    public NoticeProviderImpl(Context context) {
        mRssDatabase = new RssDatabase(context);
    }

    @Override
    public List<RssItem> getMainNotice(String category) {
        return mRssDatabase.getMainNotice(category);
    }

    @Override
    public List<RssItem> getOasisNotice() {
        return mRssDatabase.getOasisNotice();
    }

    @Override
    public List<RssItem> getStarredNotice() {
        return mRssDatabase.getStarredNotice();
    }

    @Override
    public List<RssItem> getKeywordNotice(String keyword) {
        return null;
    }
}
