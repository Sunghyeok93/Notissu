package com.notissu.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.notissu.Model.RssItem;
import com.notissu.R;
import com.notissu.SyncAdapter.SyncUtil;
import com.notissu.Util.TestUtils;

import java.util.List;

import static com.notissu.Util.LogUtils.makeLogTag;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = makeLogTag(SplashActivity.class);
    private static final int INTENT_MAIN = 0;
    // Splash screen timer
    private static final int SPLASH_TIME_OUT = 1000;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SyncUtil.CreateSyncAccount(this);

//        TestUtils.DB.addRss(getApplicationContext());
//        TestUtils.DB.getRssItemList(getApplicationContext());


        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == INTENT_MAIN) {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    // close this activity
                    finish();
                }
            }
        };

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new Thread(run).start();
            }
        }, SPLASH_TIME_OUT);
    }

    private Runnable run = new Runnable() {
        @Override
        public void run() {
            //사용자 인증 화면으로 intent
            handler.sendEmptyMessage(INTENT_MAIN);
        }
    };
}
