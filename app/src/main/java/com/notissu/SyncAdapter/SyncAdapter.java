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
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by forhack on 2016-11-26.
 */

public class SyncAdapter extends AbstractThreadedSyncAdapter {
    private static final String TAG = SyncAdapter.class.getName();
    private static final String FEED_URL = "https://leesanghyeok.github.io/feed.xml"; //내 블로그 임시
//    private static final String FEED_URL = "http://www.ssu.ac.kr/web/kor/plaza_d_01;jsessionid=yIPyJDhVJSyGG1SWk3kZeQ5qXfdbVfqihsikvlZZVAILUn5tgH2HjcX4fiQFXD40?p_p_id=EXT_MIRRORBBS&p_p_lifecycle=0&p_p_state=exclusive&p_p_mode=view&p_p_col_id=column-1&p_p_col_pos=1&p_p_col_count=2&_EXT_MIRRORBBS_struts_action=%2Fext%2Fmirrorbbs%2Frss"; //내 블로그 임시
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
            final URL location = new URL(FEED_URL);

            Log.i(TAG, "Streaming data from network: " + location);
            updateLocalFeedData(location, syncResult);

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } catch (MalformedURLException e) {
            Log.e(TAG, "Feed URL is malformed", e);
            syncResult.stats.numParseExceptions++;
            return;
        } catch (IOException e) {
            Log.e(TAG, "Error reading from network: " + e.toString());
            syncResult.stats.numIoExceptions++;
            return;
        } catch (XmlPullParserException e) {
            Log.e(TAG, "Error parsing feed: " + e.toString());
            syncResult.stats.numParseExceptions++;
            return;
        } catch (ParseException e) {
            Log.e(TAG, "Error parsing feed: " + e.toString());
            syncResult.stats.numParseExceptions++;
            return;
        } catch (RemoteException e) {
            Log.e(TAG, "Error updating database: " + e.toString());
            syncResult.databaseError = true;
            return;
        } catch (OperationApplicationException e) {
            Log.e(TAG, "Error updating database: " + e.toString());
            syncResult.databaseError = true;
            return;
        } catch (FeedException e) {
            Log.e(TAG, "Error Feed Read: " + e.toString());
            syncResult.databaseError = true;
        }
        Log.i(TAG, "Network synchronization complete");
    }

    private void updateLocalFeedData(final URL feedUrl, final SyncResult syncResult)
            throws IOException, XmlPullParserException, RemoteException,
            OperationApplicationException, ParseException, FeedException{

        InputStream feedStream = IOUtils.URLToInputStream(FEED_URL);

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
        final List<RssItem> DBReceiveRssItemList = rssDatabase.getRssItemList();
        /*DB에 있는 RSS를 모두 가져오려고 하는데, 인자가 없어도 괜찮으려나?? 없어도 괜찮긴한데,
        범용성이 떨어지니깐, 지정한 하나 가져오는건 따로 만들고, 그럼 지정해야하는게 뭐가 있지??
        테이블이야 내부에서 지정하고, 가져올 column들 그건 따로 만들까, 배열로 넣어서 그것만 가져오게 할까?
        아니면 그냥 다 가져오게 할까, 그냥 다 가져오게 하자, 그럼 컬럼도 필요없고, 조건에 맞춰서 가져와야할까?
        조건에 맞추지말고 필요할 때 메서드를 만들지 뭐, 그럼 이 인터페이스 유지해야겠다.*/


        //서버에서 읽어온 RssItem과 localDB에 저장된 RssItem과 비교해서 있는지 확인한다.
        for (int i = 0; i < DBReceiveRssItemList.size(); i++) {
            syncResult.stats.numEntries++;
            RssItem dbRssItem = DBReceiveRssItemList.get(i);
            RssItem isExist = rssMap.get(dbRssItem.getGuid());
            if (isExist != null) {  //같은게 있으면
                rssMap.remove(dbRssItem.getGuid());
                if (isExist.getTitle() != null && !isExist.getTitle().equals(dbRssItem.getTitle()) ||
                        isExist.getLink() != null && !isExist.getLink().equals(dbRssItem.getLink()) ||
                        isExist.getDescription() != null && !isExist.getDescription().equals(dbRssItem.getDescription()) ||
                        isExist.getPublishDate() != null && !isExist.getPublishDate().equals(dbRssItem.getPublishDate())) {
                    //새로 들어온것이 업데이트 할 필요가 있으면 업데이트를 한다.
                    rssDatabase.updateRss(isExist);
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
            rssDatabase.addRss(item);
            syncResult.stats.numInserts++;
        }
    }
}
