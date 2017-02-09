package com.notissu.UI.UpdatePeriod.Presenter;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.notissu.UI.UpdatePeriod.View.UpdatePeriodActivity;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by forhack on 2016-12-28.
 */

public class UpdatePeriodPresenter implements UpdatePeriodContract.Presenter {
    UpdatePeriodContract.View view;

    public UpdatePeriodPresenter(@NonNull UpdatePeriodContract.View view) {
        this.view = checkNotNull(view, "UpdatePeriodContract.View cannot be null");
        view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadTime(Context context) {
        //저장된 시간 불러오기
        long preferencePeriod = PreferenceManager
                .getDefaultSharedPreferences(context).getLong(UpdatePeriodActivity.KEY_PERIOD, 0);
        long hour = 0;
        long minute = 0;
        if (preferencePeriod != 0) {
            hour = preferencePeriod / 3600;
            minute = (preferencePeriod - (hour * 3600)) / 60;
        } else {
            hour = 0;
            minute = 0;
        }
        view.updateTime((int) hour, (int) minute);
    }

    @Override
    public void updatePeriod(Context context, int hour, int minute) {
        long period = (hour * 60 * 60) + (minute * 60);

        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putLong(UpdatePeriodActivity.KEY_PERIOD, period).commit();
    }


}
