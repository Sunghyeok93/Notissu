package com.notissu.Util;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.notissu.Database.KeywordProvider;
import com.notissu.Database.LibraryProvider;
import com.notissu.Database.LowDBProvider;
import com.notissu.Database.MainProvider;
import com.notissu.Database.NoticeProvider;
import com.notissu.Database.StarredProvider;
import com.notissu.Model.RssItem;
import com.notissu.Database.RssDatabase;

import java.util.ArrayList;
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
                return s.getMethodName() + "()";
            }
            doNext = s.getMethodName().equals("getStackTrace");
        }
        return "Can't find method name";
    }

    public static void printFail(String methodName) {
        Log.d(TAG, methodName +" : Fail");
    }

    public static void printSuccess(String methodName) {
        Log.d(TAG, methodName +" : Success");
    }

    public static void printFail(String methodName, String feature) {
        Log.d(TAG, methodName +" "+feature+" : Fail");
    }

    public static void printSuccess(String methodName, String feature) {
        Log.d(TAG, methodName +" "+feature+" : Success");
    }

    public static void printResult(final String methodName, final String feature, final boolean result) {
        if (result) {
            printSuccess(methodName, feature);
        } else {
            printFail(methodName, feature);
        }
    }

    public static class DB {
        static NoticeProvider noticeProvider;
        static MainProvider mainProvider;
        static LibraryProvider libraryProvider;
        static StarredProvider starredProvider;
        static KeywordProvider keywordProvider;
        static LowDBProvider lowDBProvider;
        public DB() {
            noticeProvider = RssDatabase.getInstance();
            mainProvider = RssDatabase.getInstance();
            libraryProvider = RssDatabase.getInstance();
            starredProvider = RssDatabase.getInstance();
            keywordProvider = RssDatabase.getInstance();
            lowDBProvider = RssDatabase.getInstance();

            Etc.getCursor();
            Etc.getNotice();
            new Notice();
            new Main();
            new Library();
            new Starred();
            new Keyword();
        }

        public static class Etc {
            private static final int FLAG_OPERATION_EQUAL = 0;
            private static final int FLAG_OPERATION_UPPER = 1;
            private static final int FLAG_OPERATION_EQUALUPPER = 2;
            private static final int FLAG_OPERATION_LOWER = 3;
            private static final int FLAG_OPERATION_EQUALLOWER = 4;
            private static final int FLAG_OPERATION_NOT = 5;

            private static boolean checkCount(final int caseCount, int returnCount, int flag) {
                boolean result = false;
                switch (flag) {
                    case FLAG_OPERATION_EQUAL:
                        result = caseCount == returnCount;
                        break;
                    case FLAG_OPERATION_UPPER:
                        result = caseCount > returnCount;
                        break;
                    case FLAG_OPERATION_EQUALUPPER:
                        result = caseCount >= returnCount;
                        break;
                    case FLAG_OPERATION_LOWER:
                        result = caseCount < returnCount;
                        break;
                    case FLAG_OPERATION_EQUALLOWER:
                        result = caseCount <= returnCount;
                        break;
                    case FLAG_OPERATION_NOT:
                        result = caseCount != returnCount;
                        break;
                    default:
                        result = caseCount == returnCount;
                        break;
                }
                if (result) {
                    return true;
                } else {
                    return false;
                }
            }

            private static boolean checkCount(final int caseCount, int returnCount) {
                if (returnCount == caseCount) {
                    return true;
                } else {
                    return false;
                }
            }

            private static int getDuplicateCount(final List<RssItem> returnList, final List<RssItem> dumyList) {
                int duplicateCount = 0;
                for (RssItem dumyItem : dumyList) {
                    for (RssItem returnItem : returnList) {
                        if (dumyItem.equals(returnItem)) {
                            //같다면
                            duplicateCount++;
                            break;
                        }
                    }
                }
                return duplicateCount;
            }

            public static void getCursor() {
                String methodName = getMethodName(Thread.currentThread().getStackTrace());
                //DB에 있는 모든 RSS를 List형태로 반환하는 메소드
                //더미 생성
                RssItem mainItem1 = new RssItem("main1","main1","main1","main1",1, RssItem.NOT_READ);
                RssItem mainItem2 = new RssItem("main2","main2","main2","main2",2, RssItem.NOT_READ);
                //Main에 RSS 2개 넣고
                mainProvider.addMainNotice(mainItem1);
                mainProvider.addMainNotice(mainItem2);
                //getCursor를 호출한다음
                Cursor cursor = lowDBProvider.getCursor(RssItem.MainNotice.TABLE_NAME, RssItem.MainNotice.COLUMN_NAME_TITLE+"=?",new String[]{"main1"});
                //제대로 나왔는지 비교하고
                boolean result;
                result = false;
                if (cursor.getCount() == 1) {
                    result = true;
                    printSuccess(methodName,"getCount()");
                } else {
                    result = false;
                    printFail(methodName,"getCount()");
                }

                result = false;
                RssItem rssItem = new RssItem(cursor);
                if (mainItem1.equals(rssItem)) {
                    result = true;
                    printSuccess(methodName,"equals()");
                } else {
                    result = false;
                    printFail(methodName,"equals()");
                }

                if (result)
                    printSuccess(methodName);
                else
                    printFail(methodName);

                //집어넣은 더미 값 삭제
                mainProvider.deleteMainNotice(mainItem1.getGuid());
                mainProvider.deleteMainNotice(mainItem2.getGuid());
            }

            public static void getNotice() {
                String methodName = getMethodName(Thread.currentThread().getStackTrace());
                // Main,Library 두 곳에서 지정한 조건에 해당하는 리스트를 모두 반환한다.
                //더미 생성
                List<RssItem> dumyDataList = new ArrayList<>();
                dumyDataList.add(new RssItem("main1","main1","main1","main1",0, RssItem.NOT_READ));
                dumyDataList.add(new RssItem("main2","main2","main2","main2",1, RssItem.NOT_READ));
                dumyDataList.add(new RssItem("library1","library1","library1","library1",2, RssItem.NOT_READ));
                dumyDataList.add(new RssItem("library2","library2","library2","library2",3, RssItem.NOT_READ));

                // Main과 Library 두 곳에 각각 2개의 RssItem을 집어넣고
                mainProvider.addMainNotice(dumyDataList.get(0));
                mainProvider.addMainNotice(dumyDataList.get(1));
                libraryProvider.addLibraryNotice(dumyDataList.get(2));
                libraryProvider.addLibraryNotice(dumyDataList.get(3));

                // getNotice를 호출한다.
                boolean result;
                // 있는거 모두 호출
                Log.d(TAG, methodName + " Case1 전부 호출");
                List<RssItem> returnList = lowDBProvider.getNotice(null,null);
                //size 체크 최대 4개이거나 4개 이상
                result = checkCount(4,returnList.size(),FLAG_OPERATION_EQUALLOWER);
                printResult(methodName,"Case1 size()",result);
                //읽어온것들 중에 같은지 체크
                int duplicateCount = getDuplicateCount(returnList,dumyDataList);
                result = checkCount(4,duplicateCount);
                printResult(methodName,"Case1 equals()",result);


                Log.d(TAG, methodName + " Case2 두개 호출");
                returnList = lowDBProvider.getNotice(
                        RssItem.Common.COLUMN_NAME_TITLE+"=? or "+RssItem.Common.COLUMN_NAME_PUBLISH_DATE+"=?",
                        new String[]{"main1","3"});

                List<RssItem> expectDumyList = new ArrayList<>();
                expectDumyList.add(dumyDataList.get(0));
                expectDumyList.add(dumyDataList.get(3));
                int expectCount = expectDumyList.size();
                //size 체크
                result = checkCount(expectCount,returnList.size());
                printResult(methodName,"Case2 size()",result);
                //읽어온거 같은지 체크
                duplicateCount = getDuplicateCount(returnList,expectDumyList);
                result = checkCount(expectCount,duplicateCount);
                printResult(methodName,"Case2 equals()",result);

                //집어넣은 더미 값 삭제
                mainProvider.deleteMainNotice(dumyDataList.get(0).getGuid());
                mainProvider.deleteMainNotice(dumyDataList.get(1).getGuid());
                libraryProvider.deleteLibraryNotice(dumyDataList.get(2).getGuid());
                libraryProvider.deleteLibraryNotice(dumyDataList.get(3).getGuid());
           }
        }

        public static class Notice {
            public Notice() {
                updateNotice();
            }

            public static void updateNotice() {
                String methodName = getMethodName(Thread.currentThread().getStackTrace());
                //인자로 들어온 RssItem을 업데이트한다.
                //이 메소드의 효과는 library나 main구별없이 rss를 넣으면 update해주는 것이다.
                //0이 아니게 나오는것을 유도해보고 잘된것
                //main에 하나 library에 하나 RssItem을 넣고,
                List<RssItem> dumyDataList = new ArrayList<>();
                dumyDataList.add(new RssItem("main2","main2","main2","main2",1, RssItem.NOT_READ));
                dumyDataList.add(new RssItem("library1","library1","library1","library1",2, RssItem.NOT_READ));
                mainProvider.addMainNotice(dumyDataList.get(0));
                libraryProvider.addLibraryNotice(dumyDataList.get(1));
                //main을 변경해본다.
                RssItem rssItem = dumyDataList.get(0);
                rssItem.setIsRead(RssItem.READ);
                noticeProvider.updateNotice(rssItem);
                //main과 library를 읽어서 값이제대로 변했나 확인한다.
                List<RssItem> mainList = mainProvider.getMainNotice(MainProvider.NOTICE_SSU_ALL);
                List<RssItem> libraryList = libraryProvider.getLibraryNotice();
                int result = 0;
                for (RssItem main : mainList) {
                    if (rssItem.equals(main)) {
                        if (main.getIsRead() == RssItem.READ)
                            result++;
                        break;
                    }
                }
                for (RssItem library : libraryList) {
                    if (dumyDataList.get(1).equals(library)) {
                        if (library.getIsRead() != RssItem.READ)
                            result++;
                        break;
                    }
                }
                if (result == 2) {
                    printResult(methodName, "isMainChanged", true);
                } else {
                    printResult(methodName, "isMainChanged", false);
                }

                //삽입한 값을 지운다.
                mainProvider.deleteMainNotice(dumyDataList.get(0).getGuid());
                libraryProvider.deleteLibraryNotice(dumyDataList.get(1).getGuid());

                //0이 나오게 유도해보자. 틀린것
                //2개를 더하쟈.
                dumyDataList = new ArrayList<>();
                dumyDataList.add(new RssItem("main2","main2","main2","main2",1, RssItem.NOT_READ));
                dumyDataList.add(new RssItem("library1","library1","library1","library1",2, RssItem.NOT_READ));
                mainProvider.addMainNotice(dumyDataList.get(0));
                libraryProvider.addLibraryNotice(dumyDataList.get(1));
                //Library를 변경해보자. Guid와 isread를 변경해서 넘겨보자. update 할 수 없도록.
                rssItem = new RssItem("library2","library1","library1","library1",2, RssItem.READ);
                result = noticeProvider.updateNotice(rssItem);
                //확인해본다.
                if (result == 0) {
                    printResult(methodName, "isZero", true);
                } else {
                    printResult(methodName, "isZero", false);
                }
                //삭제한다.
                mainProvider.deleteMainNotice(dumyDataList.get(0).getGuid());
                libraryProvider.deleteLibraryNotice(dumyDataList.get(1).getGuid());



            }
        }


        public static class Main {
            public Main() {
                addMainNotice();
                getMainNotice();
                updateMainNotice();
                getMainNotice();
                deleteMainNotice();
                getMainNotice();
            }

            //테스트 케이스
            RssItem rssItem = new RssItem("guid","title","link","descript",123, RssItem.NOT_READ);

            public void getMainNotice() {

                List<RssItem> rssItemList = mainProvider.getMainNotice(MainProvider.NOTICE_SSU_ALL);

                String methodName = getMethodName(Thread.currentThread().getStackTrace());
                if (rssItemList.size() > 0)
                    printSuccess(methodName);
                else
                    printFail(methodName);
            }

            public void addMainNotice() {

                long result = mainProvider.addMainNotice(rssItem);

                String methodName = getMethodName(Thread.currentThread().getStackTrace());
                if (result == -1)
                    printFail(methodName);
                else
                    printSuccess(methodName);
            }

            public void updateMainNotice() {
                //테스트 케이스
                RssItem rssItem = new RssItem("guid","update","update","update",123, RssItem.NOT_READ);

                int result = mainProvider.updateMainNotice(rssItem);

                String methodName = getMethodName(Thread.currentThread().getStackTrace());
                if (result <= 0)
                    printFail(methodName);
                else
                    printSuccess(methodName);
            }

            public void deleteMainNotice() {
                //테스트 케이스
                int result = mainProvider.deleteMainNotice("guid");

                String methodName = getMethodName(Thread.currentThread().getStackTrace());
                if (result <= 0)
                    printFail(methodName);
                else
                    printSuccess(methodName);
            }
        }

        public static class Library {
            public Library() {
                addLibraryNotice();
                getLibraryNotice();
                updateLibraryNotice();
                getLibraryNotice();
                deleteLibraryNotice();
                getLibraryNotice();
            }

            //테스트 케이스
            RssItem rssItem = new RssItem("guid","title","link","descript",123, RssItem.NOT_READ);

            public void getLibraryNotice() {
                List<RssItem> rssItemList = libraryProvider.getLibraryNotice();

                String methodName = getMethodName(Thread.currentThread().getStackTrace());
                if (rssItemList.size() > 0)
                    printSuccess(methodName);
                else
                    printFail(methodName);
            }

            public void addLibraryNotice() {
                long result = libraryProvider.addLibraryNotice(rssItem);

                String methodName = getMethodName(Thread.currentThread().getStackTrace());
                if (result == -1)
                    printFail(methodName);
                else
                    printSuccess(methodName);
            }

            public void updateLibraryNotice() {
                //테스트 케이스
                RssItem rssItem = new RssItem("guid","update","update","update",123, RssItem.NOT_READ);

                int result = libraryProvider.updateLibraryNotice(rssItem);

                String methodName = getMethodName(Thread.currentThread().getStackTrace());
                if (result <= 0)
                    printFail(methodName);
                else
                    printSuccess(methodName);
            }

            public void deleteLibraryNotice() {
                //테스트 케이스
                int result = libraryProvider.deleteLibraryNotice(rssItem.getGuid());

                String methodName = getMethodName(Thread.currentThread().getStackTrace());
                if (result <= 0)
                    printFail(methodName);
                else
                    printSuccess(methodName);
            }
        }

        public static class Starred {
            public Starred() {
                addStarred();
                getStarred();
                deleteStarred();
                getStarred();
            }

            //테스트 케이스
            RssItem rssItem = new RssItem("guid","title","link","descript",123, RssItem.NOT_READ);

            public void getStarred() {
                List<RssItem> rssItemList = starredProvider.getStarred();

                String methodName = getMethodName(Thread.currentThread().getStackTrace());
                if (rssItemList.size() > 0)
                    printSuccess(methodName);
                else
                    printFail(methodName);
            }

            public void addStarred() {

                long result = starredProvider.addStarred(rssItem.getTitle());

                String methodName = getMethodName(Thread.currentThread().getStackTrace());
                if (result == -1)
                    printFail(methodName);
                else
                    printSuccess(methodName);
            }

            public void deleteStarred() {

                int result = starredProvider.deleteStarred(rssItem.getTitle());

                String methodName = getMethodName(Thread.currentThread().getStackTrace());
                if (result <= 0)
                    printFail(methodName);
                else
                    printSuccess(methodName);
            }
        }

        public static class Keyword {
            public Keyword() {
                addKeyword();
                getKeywordData();
                getKeyword();
                deleteKeyword();
                getKeywordData();
                getKeyword();
            }

            //테스트 케이스
            String testCase = "keyword";
            public boolean getKeywordData() {
                List<RssItem> rssItemList = keywordProvider.getKeyword(testCase);

                String methodName = getMethodName(Thread.currentThread().getStackTrace());
                if (rssItemList.size() > 0) {
                    printSuccess(methodName);
                    return true;
                } else {
                    printFail(methodName);
                    return false;
                }
            }

            public boolean getKeyword() {
                List<String> rssItemList = keywordProvider.getKeyword();

                String methodName = getMethodName(Thread.currentThread().getStackTrace());
                if (rssItemList.size() > 0) {
                    printSuccess(methodName);
                    return true;
                } else {
                    printFail(methodName);
                    return false;
                }
            }

            public void addKeyword() {

                long result = keywordProvider.addKeyword(testCase);

                String methodName = getMethodName(Thread.currentThread().getStackTrace());
                if (result == -1)
                    printFail(methodName);
                else
                    printSuccess(methodName);
            }

            public void deleteKeyword() {
                //테스트 케이스
                int result = keywordProvider.deleteKeyword(testCase);

                String methodName = getMethodName(Thread.currentThread().getStackTrace());
                if (result <= 0)
                    printFail(methodName);
                else
                    printSuccess(methodName);
            }
        }

    }

}
