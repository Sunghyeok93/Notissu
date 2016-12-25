package com.notissu.SyncAdapter;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.notissu.Util.LogUtils;

/**
 * Created by forhack on 2016-11-26.
 */

public class SyncService extends Service {
    private static final String TAG = LogUtils.makeLogTag(SyncService.class);
    // Storage for an instance of the sync adapter
    private static SyncAdapter mSyncAdapter = null;
    // Object to use as a thread-safe lock
    private static final Object mSyncAdapterLock = new Object();
    /*
     * Instantiate the sync adapter object.
     */

    @Override
    public void onCreate() {
        /*
         * Create the sync adapter as a singleton.
         * Set the sync adapter as syncable
         * Disallow parallel syncs
         */
        synchronized (mSyncAdapterLock) {
            if (mSyncAdapter == null) {
                mSyncAdapter = new SyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mSyncAdapter.getSyncAdapterBinder();
    }
}
