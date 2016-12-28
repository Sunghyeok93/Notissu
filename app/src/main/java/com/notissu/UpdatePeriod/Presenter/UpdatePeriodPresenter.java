package com.notissu.UpdatePeriod.Presenter;

import android.content.Context;
import android.preference.PreferenceManager;

import com.notissu.SyncAdapter.SyncUtil;

import static com.notissu.UpdatePeriod.View.UpdatePeriodActivity.KEY_PERIOD;

/**
 * Created by forhack on 2016-12-28.
 */

public class UpdatePeriodPresenter implements UpdatePeriodContract.Presenter {
    UpdatePeriodContract.View view;

    @Override
    public void attachView(UpdatePeriodContract.View view) {
        this.view = view;
    }

    @Override
    public void loadTime(Context context) {
        //저장된 시간 불러오기
        long preferencePeriod = PreferenceManager
                .getDefaultSharedPreferences(context).getLong(KEY_PERIOD, 0);
        long hour = 0;
        long minute = 0;
        if (preferencePeriod != 0) {
            hour = preferencePeriod / 3600;
            minute = (preferencePeriod - (hour * 3600)) / 60;
        } else {
            hour = 0;
            minute = 0;
        }
        view.updateTime((int)hour,(int)minute);
    }

    @Override
    public void updatePeriod(Context context, int hour, int minute) {
        long period = (hour*60*60) + (minute*60);

        SyncUtil.updateSyncFrequency(period);

        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putLong(KEY_PERIOD, period).commit();
    }

    @Override
    public void detachView() {
        view = null;
    }
}
