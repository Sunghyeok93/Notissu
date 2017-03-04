package com.notissu;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by forhack on 2017-02-06.
 */

public class BaseApplication extends Application {
    public static final String TAG = BaseApplication.class.getSimpleName();
    private static RequestQueue mQueue;
    public static final String BASE_URL = "http://1004xlsrn.gonetis.com:8080/";

    @Override
    public void onCreate() {
        super.onCreate();

        databaseSettings();

        networkSettings();

    }


    private void databaseSettings() {
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfig);
    }

    private void networkSettings() {
        mQueue = Volley.newRequestQueue(getApplicationContext());
    }

    public static RequestQueue getRequestQueue() {
        return mQueue;
    }
}
