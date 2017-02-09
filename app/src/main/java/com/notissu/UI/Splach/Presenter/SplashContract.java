package com.notissu.UI.Splach.Presenter;

import android.content.Context;

import com.notissu.BasePresenter;
import com.notissu.BaseView;

/**
 * Created by forhack on 2017-01-01.
 */

public interface SplashContract {
    int INTENT_MAIN = 0;
    int SPLASH_TIME_OUT = 1000;

    interface View extends BaseView<Presenter> {

        void showMain();
    }

    interface Presenter extends BasePresenter {

    }

}
