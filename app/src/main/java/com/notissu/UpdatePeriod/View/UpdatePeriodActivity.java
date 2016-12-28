package com.notissu.UpdatePeriod.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.notissu.R;
import com.notissu.UpdatePeriod.Presenter.UpdatePeriodContract;
import com.notissu.UpdatePeriod.Presenter.UpdatePeriodPresenter;
import com.notissu.Util.LogUtils;
import com.shawnlin.numberpicker.NumberPicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdatePeriodActivity extends AppCompatActivity implements UpdatePeriodContract.View{
    public static final String TAG = LogUtils.makeLogTag(UpdatePeriodActivity.class);
    public static final String KEY_PERIOD = "KEY_PERIOD";

    @BindView(R.id.update_period_toolbar)
    Toolbar toolbar;
    @BindView(R.id.update_period_tv_period)
    TextView mTvPeriod;
    @BindView(R.id.update_period_np_hour)
    NumberPicker mNpHour;
    @BindView(R.id.update_period_np_minute)
    NumberPicker mNpMinute;

    private UpdatePeriodContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_period);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        presenter = new UpdatePeriodPresenter();
        presenter.attachView(this);
        presenter.loadTime(getApplicationContext());

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @OnClick(R.id.update_period_btn_update)
    void onUpdatePeriod() {
        presenter.updatePeriod(getApplicationContext(), mNpHour.getValue(), mNpMinute.getValue());
        finish();
    }

    @Override
    public void updateTime(int hour, int minute) {
        mTvPeriod.setText(hour + "시간 " + minute + "분");
        mNpHour.setValue(hour);
        mNpMinute.setValue(minute);
    }
}
