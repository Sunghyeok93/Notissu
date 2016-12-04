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
        public DB(Context context) {
            addRss(context);
            getRssItemList(context);
            updateRss(context);
            deleteRss(context);
        }

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

        public static void updateRss(Context context) {
            RssDatabase rssDatabase = new RssDatabase(context);
            //테스트 케이스
            RssItem rssItem = new RssItem("guid","update","update","update","update");
            int result = rssDatabase.updateRss(rssItem);
            if (result <= 0)
                Log.d(TAG, "updateRss() : Fail");
            else
                Log.d(TAG, "updateRss() : Success");
        }

        public static void deleteRss(Context context) {
            RssDatabase rssDatabase = new RssDatabase(context);
            //테스트 케이스
            int result = rssDatabase.deleteRss("guid");
            if (result <= 0)
                Log.d(TAG, "deleteRss() : Fail");
            else
                Log.d(TAG, "deleteRss() : Success");
        }

    }

}
