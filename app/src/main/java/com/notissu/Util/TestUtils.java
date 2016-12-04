package com.notissu.Util;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;

import com.notissu.Model.RssItem;
import com.notissu.SyncAdapter.RssDatabase;

import java.util.List;

import static com.notissu.Util.LogUtils.makeLogTag;

/**
 * Created by forhack on 2016-12-04.
 */

public class TestUtils {
    private static final String TAG = makeLogTag(TestUtils.class);

    public static class DB {

        public static void getRssItemList(Context context) {
            RssDatabase rssDatabase = new RssDatabase(context);
            List<RssItem> rssItemList = rssDatabase.getRssItemList();

            if (rssItemList.size() > 0)
                Log.d(TAG, "getRssItemList() : Success");
            else
                Log.d(TAG, "getRssItemList() : Fail");
        }

        public static void addRss(Context context) {
            RssDatabase rssDatabase = new RssDatabase(context);
            //테스트 케이스
            RssItem rssItem = new RssItem("guid","title","link","publish","descript");

            long result = rssDatabase.addRss(rssItem);
            if (result == -1)
                Log.d(TAG, "addRss() : Fail");
            else
                Log.d(TAG, "addRss() : Success");
        }
    }

}
