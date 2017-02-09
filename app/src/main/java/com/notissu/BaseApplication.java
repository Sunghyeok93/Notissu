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
    private static RequestQueue mQueue;
    public static final String BASE_URL = "http://1004xlsrn.gonetis.com:8500/";

    @Override
    public void onCreate() {
        super.onCreate();
        mQueue = Volley.newRequestQueue(getApplicationContext());

        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfig);
    }

    public static RequestQueue getRequestQueue() {
        return mQueue;
    }
}
