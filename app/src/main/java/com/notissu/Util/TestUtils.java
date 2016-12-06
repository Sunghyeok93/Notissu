package com.notissu.Util;

import android.content.Context;
import android.util.Log;

import com.notissu.Model.RssItem;
import com.notissu.SyncAdapter.NoticeProvider;
import com.notissu.SyncAdapter.RssDatabase;

import java.util.List;

import static com.notissu.Util.LogUtils.makeLogTag;

/**
 * Created by forhack on 2016-12-04.
 */

public class TestUtils {
    private static final String TAG = makeLogTag(TestUtils.class);

    public static String getMethodName(StackTraceElement e[]) {
        boolean doNext = false;
        for (StackTraceElement s : e) {
            if (doNext) {
                return s.getMethodName();
            }
            doNext = s.getMethodName().equals("getStackTrace");
        }
        return "Can't find method name";
    }

    public static void printFail(String methodName) {
        Log.d(TAG, methodName +"() : Fail");
    }

    public static void printSuccess(String methodName) {
        Log.d(TAG, methodName +"() : Success");
    }

    public static class DB {
        public DB(Context context) {
            new Main(context);
            new Library(context);
            new Starred(context);
            new Keyword(context);
        }

        public static class Main {
            public Main(Context context) {
                addMainNotice(context);
                getMainNotice(context);
                updateMainNotice(context);
                getMainNotice(context);
                deleteMainNotice(context);
                getMainNotice(context);
            }

            //테스트 케이스
            RssItem rssItem = new RssItem("guid","title","link","descript","publish");

            public void getMainNotice(Context context) {
                RssDatabase rssDatabase = new RssDatabase(context);
                List<RssItem> rssItemList = rssDatabase.getMainNotice(NoticeProvider.NOTICE_SSU_ALL);

                String methodName = getMethodName(Thread.currentThread().getStackTrace());
                if (rssItemList.size() > 0)
                    printSuccess(methodName);
                else
                    printFail(methodName);
            }

            public void addMainNotice(Context context) {
                RssDatabase rssDatabase = new RssDatabase(context);

                long result = rssDatabase.addMainNotice(rssItem);

                String methodName = getMethodName(Thread.currentThread().getStackTrace());
                if (result == -1)
                    printFail(methodName);
                else
                    printSuccess(methodName);
            }

            public void updateMainNotice(Context context) {
                RssDatabase rssDatabase = new RssDatabase(context);
                //테스트 케이스
                RssItem rssItem = new RssItem("guid","update","update","update","update");

                int result = rssDatabase.updateMainNotice(rssItem);

                String methodName = getMethodName(Thread.currentThread().getStackTrace());
                if (result <= 0)
                    printFail(methodName);
                else
                    printSuccess(methodName);
            }

            public void deleteMainNotice(Context context) {
                RssDatabase rssDatabase = new RssDatabase(context);
                //테스트 케이스
                int result = rssDatabase.deleteMainNotice("guid");

                String methodName = getMethodName(Thread.currentThread().getStackTrace());
                if (result <= 0)
                    printFail(methodName);
                else
                    printSuccess(methodName);
            }
        }

        public static class Library {
            public Library(Context context) {
                addLibraryNotice(context);
                getLibraryNotice(context);
                updateLibraryNotice(context);
                getLibraryNotice(context);
                deleteLibraryNotice(context);
                getLibraryNotice(context);
            }

            //테스트 케이스
            RssItem rssItem = new RssItem("guid","title","link","descript","publish");

            public void getLibraryNotice(Context context) {
                RssDatabase rssDatabase = new RssDatabase(context);
                List<RssItem> rssItemList = rssDatabase.getLibraryNotice();

                String methodName = getMethodName(Thread.currentThread().getStackTrace());
                if (rssItemList.size() > 0)
                    printSuccess(methodName);
                else
                    printFail(methodName);
            }

            public void addLibraryNotice(Context context) {
                RssDatabase rssDatabase = new RssDatabase(context);

                long result = rssDatabase.addLibraryNotice(rssItem);

                String methodName = getMethodName(Thread.currentThread().getStackTrace());
                if (result == -1)
                    printFail(methodName);
                else
                    printSuccess(methodName);
            }

            public void updateLibraryNotice(Context context) {
                RssDatabase rssDatabase = new RssDatabase(context);
                //테스트 케이스
                RssItem rssItem = new RssItem("guid","update","update","update","update");

                int result = rssDatabase.updateLibraryNotice(rssItem);

                String methodName = getMethodName(Thread.currentThread().getStackTrace());
                if (result <= 0)
                    printFail(methodName);
                else
                    printSuccess(methodName);
            }

            public void deleteLibraryNotice(Context context) {
                RssDatabase rssDatabase = new RssDatabase(context);
                //테스트 케이스
                int result = rssDatabase.deleteLibraryNotice(rssItem.getGuid());

                String methodName = getMethodName(Thread.currentThread().getStackTrace());
                if (result <= 0)
                    printFail(methodName);
                else
                    printSuccess(methodName);
            }
        }

        public static class Starred {
            public Starred(Context context) {
                addStarred(context);
                getStarred(context);
                deleteStarred(context);
                getStarred(context);
            }

            //테스트 케이스
            RssItem rssItem = new RssItem("guid","title","link","descript","publish");

            public void getStarred(Context context) {
                RssDatabase rssDatabase = new RssDatabase(context);
                List<RssItem> rssItemList = rssDatabase.getStarred();

                String methodName = getMethodName(Thread.currentThread().getStackTrace());
                if (rssItemList.size() > 0)
                    printSuccess(methodName);
                else
                    printFail(methodName);
            }

            public void addStarred(Context context) {
                RssDatabase rssDatabase = new RssDatabase(context);

                long result = rssDatabase.addStarred(rssItem.getGuid());

                String methodName = getMethodName(Thread.currentThread().getStackTrace());
                if (result == -1)
                    printFail(methodName);
                else
                    printSuccess(methodName);
            }

            public void deleteStarred(Context context) {
                RssDatabase rssDatabase = new RssDatabase(context);

                int result = rssDatabase.deleteStarred(rssItem.getGuid());

                String methodName = getMethodName(Thread.currentThread().getStackTrace());
                if (result <= 0)
                    printFail(methodName);
                else
                    printSuccess(methodName);
            }
        }

        public static class Keyword {
            public Keyword(Context context) {
                addKeyword(context);
                getKeywordData(context);
                getKeyword(context);
                deleteKeyword(context);
                getKeywordData(context);
                getKeyword(context);
            }

            //테스트 케이스
            String testCase = "keyword";

            public boolean getKeywordData(Context context) {
                RssDatabase rssDatabase = new RssDatabase(context);
                List<RssItem> rssItemList = rssDatabase.getKeyword(testCase);

                String methodName = getMethodName(Thread.currentThread().getStackTrace());
                if (rssItemList.size() > 0) {
                    printSuccess(methodName);
                    return true;
                } else {
                    printFail(methodName);
                    return false;
                }
            }

            public boolean getKeyword(Context context) {
                RssDatabase rssDatabase = new RssDatabase(context);
                List<String> rssItemList = rssDatabase.getKeyword();

                String methodName = getMethodName(Thread.currentThread().getStackTrace());
                if (rssItemList.size() > 0) {
                    printSuccess(methodName);
                    return true;
                } else {
                    printFail(methodName);
                    return false;
                }
            }

            public void addKeyword(Context context) {
                RssDatabase rssDatabase = new RssDatabase(context);

                long result = rssDatabase.addKeyword(testCase);

                String methodName = getMethodName(Thread.currentThread().getStackTrace());
                if (result == -1)
                    printFail(methodName);
                else
                    printSuccess(methodName);
            }

            public void deleteKeyword(Context context) {
                RssDatabase rssDatabase = new RssDatabase(context);
                //테스트 케이스
                int result = rssDatabase.deleteKeyword(testCase);

                String methodName = getMethodName(Thread.currentThread().getStackTrace());
                if (result <= 0)
                    printFail(methodName);
                else
                    printSuccess(methodName);
            }
        }

    }

}
