package com.notissu.UI.Splach;

public interface SplashContract {
    int INTENT_MAIN = 0;
    int SPLASH_TIME_OUT = 1000;

    interface View {

        void showMain();
    }

    interface Presenter {

        void start();
    }

}
