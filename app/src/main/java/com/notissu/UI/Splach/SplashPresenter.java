package com.notissu.UI.Splach;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by forhack on 2017-01-01.
 */

public class SplashPresenter implements SplashContract.Presenter {
    SplashContract.View mView;

    public SplashPresenter(@NonNull SplashContract.View view) {
        this.mView = checkNotNull(view);
    }

    @Override
    public void start() {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == SplashContract.INTENT_MAIN) {
                    mView.showMain();
                }
            }
        };
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(SplashContract.INTENT_MAIN);
                    }
                }).start();
            }
        }, SplashContract.SPLASH_TIME_OUT);
    }
}
