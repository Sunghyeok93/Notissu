package com.notissu.UI.UpdatePeriod.Presenter;

import android.content.Context;

import com.notissu.BasePresenter;
import com.notissu.BaseView;

/**
 * Created by forhack on 2016-12-28.
 */

public interface UpdatePeriodContract {
    interface View extends BaseView<Presenter>{
        void updateTime(int hour, int minute);
    }

    interface Presenter extends BasePresenter{

        void loadTime(Context context);

        void updatePeriod(Context context, int hour, int minute);
    }
}
