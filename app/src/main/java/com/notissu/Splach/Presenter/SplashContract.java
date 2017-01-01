package com.notissu.Splach.Presenter;

import android.content.Context;

import com.notissu.BasePresenter;
import com.notissu.BaseView;

/**
 * Created by forhack on 2017-01-01.
 */

public interface SplashContract {
    interface View extends BaseView<Presenter> {

        void showMain();
    }

    interface Presenter extends BasePresenter {

        void setPreference(Context context);
    }

}
