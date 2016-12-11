package com.notissu.Activity;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.notissu.R;
import com.notissu.SyncAdapter.SyncUtil;
import com.shawnlin.numberpicker.NumberPicker;

public class UpdatePeriodActivity extends AppCompatActivity {
    private static final String KEY_PERIOD = "KEY_PERIOD";

    Button mBtnUpdate;
    TextView mTvPeriod;
    NumberPicker mNpHour;
    NumberPicker mNpMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_period);

        /*위젯을 초기화하는 함수*/
        initWidget();
        /*초기화한 위젯에 데이터를 처리하는 함수*/
        settingWidget();
        /*위젯에 리스터를 붙여주는 함수*/
        settingListener();
    }

    private void initWidget() {
        mBtnUpdate = (Button) findViewById(R.id.update_period_btn_update);
        mTvPeriod = (TextView) findViewById(R.id.update_period_tv_period);
        mNpHour = (NumberPicker) findViewById(R.id.update_period_np_hour);
        mNpMinute = (NumberPicker) findViewById(R.id.update_period_np_minute);
    }

    private void settingWidget() {
        //저장된 시간 불러오기
        long preferencePeriod = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext()).getLong(KEY_PERIOD, 0);
        long hour = 0;
        long minute = 0;
        if (preferencePeriod != 0) {
            hour = preferencePeriod / 3600;
            minute = (preferencePeriod - (hour * 3600)) / 60;
        } else {
            hour = 0;
            minute = 0;
        }
        mTvPeriod.setText(hour + "시간 " + minute + "분");
        mNpHour.setValue((int)hour);
        mNpMinute.setValue((int)minute);
    }

    private void settingListener() {
        mBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePeriod();
                finish();
            }
        });
    }

    private void updatePeriod() {
        int hour = mNpHour.getValue();
        int minute = mNpMinute.getValue();
        long period = (hour*60*60) + (minute*60);

        SyncUtil.updateSyncFrequency(period);


        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit()
                .putLong(KEY_PERIOD, period).commit();
    }
}
