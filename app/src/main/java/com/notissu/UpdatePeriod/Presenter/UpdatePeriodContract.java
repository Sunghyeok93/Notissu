package com.notissu.UpdatePeriod.Presenter;

import android.content.Context;

/**
 * Created by forhack on 2016-12-28.
 */

public interface UpdatePeriodContract {
    interface View {
        void updateTime(int hour, int minute);
    }

    interface Presenter {
        void attachView(View view);

        void detachView();

        void loadTime(Context context);

        void updatePeriod(Context context, int hour, int minute);
    }
}
