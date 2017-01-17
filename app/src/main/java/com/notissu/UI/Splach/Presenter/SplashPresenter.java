package com.notissu.UI.Splach.Presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;

import com.notissu.Database.RssDatabase;
import com.notissu.SyncAdapter.SyncUtil;
import com.notissu.Util.ResString;
import com.notissu.Util.TestUtils;
import com.notissu.UI.Splach.View.SplashActivity;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by forhack on 2017-01-01.
 */

public class SplashPresenter implements SplashContract.Presenter {
    SplashContract.View view;

    public SplashPresenter(@NonNull SplashContract.View view) {
        this.view = checkNotNull(view);

        view.setPresenter(this);
    }

    @Override
    public void start() {
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == SplashActivity.INTENT_MAIN) {
                    view.showMain();
                }
            }
        };
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(SplashActivity.INTENT_MAIN);
                    }
                }).start();
            }
        }, SplashActivity.SPLASH_TIME_OUT);
    }

    @Override
    public void setPreference(Context context) {
        /*앱이 실행되면 가장 먼저 호출되어야 할 기능이다.
        resource에서 문자열 읽어들기 편하게 하기위해 만든 Util.
        singleton으로 되어있기에 최초에 context를 넘겨줘야함.*/
        ResString.getInstance().setContext(context);
        SyncUtil.CreateSyncAccount(context);
        RssDatabase.setInstance(context);

        //DB테스트코드
        new TestUtils.DB();
    }
}
