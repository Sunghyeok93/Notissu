package com.notissu.SyncAdapter;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.SyncResult;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import com.notissu.Model.RssItem;
import com.notissu.R;
import com.notissu.Util.IOUtils;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by forhack on 2016-11-26.
 */

public class SyncAdapter extends AbstractThreadedSyncAdapter {
    private static final String TAG = SyncAdapter.class.getName();
//    private static final String MAIN_NOTICE_URL = "https://leesanghyeok.github.io/feed.xml"; //내 블로그 임시
    private static final String LIBRARY_NOTICE_URL = "http://oasis.ssu.ac.kr/API/BBS/1"; //도서관 공지사항
    private static final String MAIN_NOTICE_URL = "http://www.ssu.ac.kr/web/kor/plaza_d_01;jsessionid=yIPyJDhVJSyGG1SWk3kZeQ5qXfdbVfqihsikvlZZVAILUn5tgH2HjcX4fiQFXD40?p_p_id=EXT_MIRRORBBS&p_p_lifecycle=0&p_p_state=exclusive&p_p_mode=view&p_p_col_id=column-1&p_p_col_pos=1&p_p_col_count=2&_EXT_MIRRORBBS_struts_action=%2Fext%2Fmirrorbbs%2Frss"; //내 블로그 임시
    ContentResolver mContentResolver;

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContentResolver = context.getContentResolver();
    }

    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        mContentResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult) {
        Log.i(TAG, "Beginning network synchronization");
        try {
            // 학교, 도서관 공지사항 읽어오기,
            final List<RssItem> receiveMainNotices = getServerNotice(MAIN_NOTICE_URL);
            final List<RssItem> receiveLibraryNotices = getServerNotice(LIBRARY_NOTICE_URL);

            // 학교, 도서관 공지사항에서 "[공지]" 라는 문자열을 제거한다.
            final String removeWord = "[공지]";
            removeString(receiveMainNotices,removeWord);
            removeString(receiveLibraryNotices,removeWord);

            // 그리고 "[***]" 라고 들어가 있는 문자열 중에서 ***만 빼서 category로 구별해서 넣는다.
            for (RssItem rssItem : receiveMainNotices) {
                String title = rssItem.getTitle();
                final String[] category = NoticeProvider.NOTICE_CATEGORY;
                for (int i = 0; i < category.length; i++) {
                    if (title.contains("[" + category[i] + "]")) {
                        rssItem.setCategory(category[i]);
                    } else {
                        Log.w(TAG,"Category can not find!");
                    }
                }
            }

            // 학교, 도서관 공지사항을 Map에 데이터 넣기.
            HashMap<String, RssItem> mainMap = transToMap(receiveMainNotices);
            HashMap<String, RssItem> libraryMap = transToMap(receiveLibraryNotices);

            //DB에 저장되어 있는 MainNotice, LibraryNotice RssItem을 불러들인다.
            RssDatabase rssDatabase = RssDatabase.getInstance();
            final List<RssItem> DBMainNotice = rssDatabase.getMainNotice(NoticeProvider.NOTICE_SSU_ALL);
            final List<RssItem> DBLibararyNotice = rssDatabase.getLibraryNotice();

            // DB에 등록된 정보를 모두 읽으며 Guid로 Map과 비교해서
            // 같은게 있으면
                    // Map안에 데이터를 지우고
                    // 업데이트할 필요가 있다면 업데이트한다.
            updateNotice(DBMainNotice,mainMap,rssDatabase,syncResult);
            updateNotice(DBLibararyNotice,libraryMap,rssDatabase,syncResult);

            // 키워드에 등록된 모든 값을 가져온다.
            final List<String> keywordList = rssDatabase.getKeyword();

            /*
            새롭게 입련된 것들의 Title과 키워드를 비교해본다.
            매칭된다면 푸시메시지 보낸다.
            */
            compareTitle(mainMap,keywordList);
            compareTitle(libraryMap,keywordList);

            // Main을 DB에 넣는다.
            for (RssItem item : mainMap.values()) {
                rssDatabase.addMainNotice(item);
                syncResult.stats.numInserts++;
            }

            // Library를 DB에 넣는다.
            for (RssItem item : libraryMap.values()) {
                rssDatabase.addLibraryNotice(item);
                syncResult.stats.numInserts++;
            }

        } catch (MalformedURLException e) {
            Log.e(TAG, "Feed URL is malformed", e);
            syncResult.stats.numParseExceptions++;
            return;
        } catch (IOException e) {
            Log.e(TAG, "Error reading from network: " + e.toString());
            syncResult.stats.numIoExceptions++;
            return;
        } catch (FeedException e) {
            Log.e(TAG, "Error Feed Read: " + e.toString());
            syncResult.databaseError = true;
        }
        Log.i(TAG, "Network synchronization complete");
    }

    /*
    새롭게 입련된 것들의 Title과 키워드를 비교해본다.
    매칭된다면 푸시메시지 보낸다.
    */
    private void compareTitle(HashMap<String,RssItem> map, List<String> keywordList) {
        for (String keyword : keywordList) {
            for (RssItem item : map.values()) {
                String mapTitle = item.getTitle();
                if (mapTitle.contains(keyword)) { // 매칭된다면
                    // 푸시알림을 한다.
                    Log.d(TAG,"push push");
                }
            }
        }
    }

    /*
    DB에 등록된 정보를 모두 읽으며 Guid로 맵과 비교.
    같은게 있으면
    Map안에 데이터를 지우고
    업데이트할 필요가 있다면 업데이트한다.
    */
    private void updateNotice(List<RssItem> dbRssItems, HashMap<String, RssItem> receiveMap,
                          RssDatabase rssDatabase, SyncResult syncResult) {
        for (RssItem dbRssItem : dbRssItems) {
            syncResult.stats.numEntries++;
            RssItem existedItem = receiveMap.get(dbRssItem.getGuid());
            if (existedItem != null) {  //같은게 있으면
                receiveMap.remove(dbRssItem.getGuid()); // map에서 지우고
                if (existedItem.getTitle() != null && !existedItem.getTitle().equals(dbRssItem.getTitle()) ||
                        existedItem.getLink() != null && !existedItem.getLink().equals(dbRssItem.getLink()) ||
                        existedItem.getDescription() != null && !existedItem.getDescription().equals(dbRssItem.getDescription()) ||
                        existedItem.getPublishDate() != null && !existedItem.getPublishDate().equals(dbRssItem.getPublishDate()) ||
                        existedItem.getCategory() != null && !existedItem.getCategory().equals(dbRssItem.getCategory())) {
                    //새로 들어온것이 업데이트 할 필요가 있으면 업데이트를 한다.
                    rssDatabase.updateMainNotice(existedItem);
                    syncResult.stats.numUpdates++;
                }
            }
        }
    }

    private HashMap<String, RssItem> transToMap(List<RssItem> rssItems) {
        HashMap<String, RssItem> rssMap = new HashMap<String, RssItem>();
        for (int i = 0; i < rssItems.size(); i++) {
            RssItem item = rssItems.get(i);
            rssMap.put(item.getGuid(), item);
        }
        return rssMap;
    }

    private List<RssItem> getServerNotice(String url) throws
            IOException, FeedException{
        InputStream feedStream = IOUtils.URLToInputStream(url);

        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(feedStream));

        //서버에서 읽어온 RssItem hashMap에 집어넣는다. <id, value>
        List<SyndEntry> syndEntries = feed.getEntries();
        return RssItem.toRssList(syndEntries);
    }

    private void removeString(List<RssItem> notices, String word) {
        for (RssItem rssItems : notices) {
            String removed = rssItems.getTitle().replace(word,"").trim();
            rssItems.setTitle(removed);
        }
    }

    /*private void updateLocalRssData(final SyncResult syncResult)
            throws IOException, XmlPullParserException, RemoteException,
            OperationApplicationException, ParseException, FeedException{

        InputStream feedStream = IOUtils.URLToInputStream(feedUrl);

        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(feedStream));

        //서버에서 읽어온 RssItem hashMap에 집어넣는다. <id, value>
        List<SyndEntry> syndEntries = feed.getEntries();
        final List<RssItem> receiveRssItemList = RssItem.toRssList(syndEntries);

        HashMap<String, RssItem> rssMap = new HashMap<String, RssItem>();
        for (int i = 0; i < receiveRssItemList.size(); i++) {
            RssItem item = receiveRssItemList.get(i);
            rssMap.put(item.getGuid(), item);
        }

        //DB에 저장되어 있는 모든 RssItem을 불러들인다.
        RssDatabase rssDatabase = new RssDatabase(getContext());
        final List<RssItem> DBRssItemList = rssDatabase.getCursor();

        //서버에서 읽어온 RssItem과 localDB에 저장된 RssItem과 비교해서 있는지 확인한다.
        for (int i = 0; i < DBRssItemList.size(); i++) {
            syncResult.stats.numEntries++;
            RssItem dbRssItem = DBRssItemList.get(i);
            RssItem isExist = rssMap.get(dbRssItem.getGuid());
            if (isExist != null) {  //같은게 있으면
                rssMap.remove(dbRssItem.getGuid());
                if (isExist.getTitle() != null && !isExist.getTitle().equals(dbRssItem.getTitle()) ||
                        isExist.getLink() != null && !isExist.getLink().equals(dbRssItem.getLink()) ||
                        isExist.getDescription() != null && !isExist.getDescription().equals(dbRssItem.getDescription()) ||
                        isExist.getPublishDate() != null && !isExist.getPublishDate().equals(dbRssItem.getPublishDate())) {
                    //새로 들어온것이 업데이트 할 필요가 있으면 업데이트를 한다.
                    rssDatabase.updateMainNotice(isExist);
                    syncResult.stats.numUpdates++;
                } else {
                    //업데이트할 필요가 없으면 아무것도 안한다.
                }
            } else {    //같은게 없으면
                //냅둔다
            }
        }
        for (RssItem item : rssMap.values()) {
            //DB에 추가한다.
            rssDatabase.addMainNotice(item);
            syncResult.stats.numInserts++;
        }
    }*/
}
