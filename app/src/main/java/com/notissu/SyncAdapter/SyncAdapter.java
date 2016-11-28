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

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;

/**
 * Created by forhack on 2016-11-26.
 */

public class SyncAdapter extends AbstractThreadedSyncAdapter {
    private static final String TAG = SyncAdapter.class.getName();
    private static final String FEED_URL = "https://leesanghyeok.github.io/feed.xml"; //내 블로그 임시

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
        Log.d(TAG,"helloworld");
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(feedUrl));

        Log.d(TAG,feed+"");
    }
}
