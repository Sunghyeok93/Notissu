package com.notissu.SyncAdapter;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.notissu.UpdatePeriod.View.UpdatePeriodActivity;
import com.notissu.Util.LogUtils;

/**
 * Created by forhack on 2016-11-26.
 */

public class SyncUtil {
    private static final String TAG = LogUtils.makeLogTag(SyncUtil.class);

    public static final String ACCOUNT_TYPE = "com.notissu.account";
    private static final String PREF_SETUP_COMPLETE = "PREF_SETUP_COMPLETE";
    private static final String CONTENT_AUTHORITY = RSSProvider.AUTHORITIES;
    private static final long SYNC_FREQUENCY = 60*60;// 최소 1초부터라는데 1초는 안되고 최소 1분부터다


    public static void CreateSyncAccount(Context context) {
        boolean newAccount = false;
        boolean setupComplete = PreferenceManager
                .getDefaultSharedPreferences(context).getBoolean(PREF_SETUP_COMPLETE, false);

        Account account = AuthenticatorService.getAccount(ACCOUNT_TYPE);
        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        if (accountManager.addAccountExplicitly(account, null, null)) {
            // Inform the system that this account supports sync
            ContentResolver.setIsSyncable(account, CONTENT_AUTHORITY, 1);
            // Inform the system that this account is eligible for auto sync when the network is up
            ContentResolver.setSyncAutomatically(account, CONTENT_AUTHORITY, true);
            // Recommend a schedule for automatic synchronization. The system may modify this based
            // on other scheduled syncs and network utilization.
            ContentResolver.addPeriodicSync(
                    account, CONTENT_AUTHORITY, new Bundle(),SYNC_FREQUENCY);

            newAccount = true;
        }
        // Schedule an initial sync if we detect problems with either our account or our local
        // data has been deleted. (Note that it's possible to clear app data WITHOUT affecting
        // the account list, so wee need to check both.)
        if (newAccount || !setupComplete) {
            TriggerRefresh();
            PreferenceManager.getDefaultSharedPreferences(context).edit()
                    .putLong(UpdatePeriodActivity.KEY_PERIOD, SYNC_FREQUENCY).commit();
            PreferenceManager.getDefaultSharedPreferences(context).edit()
                    .putBoolean(PREF_SETUP_COMPLETE, true).commit();
        }
    }

    public static void updateSyncFrequency(long period) {
        Account account = AuthenticatorService.getAccount(ACCOUNT_TYPE);

        ContentResolver.addPeriodicSync(
                account, CONTENT_AUTHORITY, new Bundle(),period);

    }


    /**
     * Helper method to trigger an immediate sync ("refresh").
     *
     * <p>This should only be used when we need to preempt the normal sync schedule. Typically, this
     * means the user has pressed the "refresh" button.
     *
     * Note that SYNC_EXTRAS_MANUAL will cause an immediate sync, without any optimization to
     * preserve battery life. If you know new data is available (perhaps via a GCM notification),
     * but the user is not actively waiting for that data, you should omit this flag; this will give
     * the OS additional freedom in scheduling your sync request.
     */
    public static void TriggerRefresh() {
        Bundle bundle = new Bundle();
        // Disable sync backoff and ignore sync preferences. In other words...perform sync NOW!
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(
                AuthenticatorService.getAccount(ACCOUNT_TYPE), // Sync account
                CONTENT_AUTHORITY,                 // Content authority
                bundle);                                             // Extras
    }

}
