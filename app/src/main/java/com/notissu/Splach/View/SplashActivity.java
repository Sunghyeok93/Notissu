package com.notissu.Splach.View;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.crashlytics.android.Crashlytics;
import com.notissu.Activity.MainActivity;
import com.notissu.R;
import com.notissu.Splach.Presenter.SplashContract;
import com.notissu.Splach.Presenter.SplashPresenter;

import io.fabric.sdk.android.Fabric;

import static com.notissu.Util.LogUtils.makeLogTag;

public class SplashActivity extends AppCompatActivity implements SplashContract.View{
    private static final String TAG = makeLogTag(SplashActivity.class);
    public static final int INTENT_MAIN = 0;
    // Splash screen timer
    public static final int SPLASH_TIME_OUT = 1000;
    Handler handler;

    SplashContract.Presenter presenter;

    @Override
    public void setPresenter(SplashContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);

        presenter = new SplashPresenter(this);
        presenter.setPreference(this);
        presenter.start();
    }

    @Override
    public void showMain() {
        Intent i = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(i);
        // close this activity
        finish();
    }
}
