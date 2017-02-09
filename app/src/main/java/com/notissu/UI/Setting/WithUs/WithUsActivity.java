package com.notissu.UI.Setting.WithUs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.notissu.R;
import com.notissu.Util.ResString;

import java.util.ArrayList;

public class WithUsActivity extends AppCompatActivity {
    Toolbar mToolbar;
    ListView mLvList;
    WithUsAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_us);
        mToolbar = (Toolbar) findViewById(R.id.with_us_toolbar);
        setSupportActionBar(mToolbar);
        /*위젯을 초기화하는 함수*/
        initWidget();
        /*초기화한 위젯에 데이터를 처리하는 함수*/
        settingWidget();
        /*위젯에 리스터를 붙여주는 함수*/
        settingListener();
    }

    private void initWidget() {
        mLvList = (ListView) findViewById(R.id.with_us_lv_list);

    }

    private void settingWidget() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String[] imgName = ResString.getInstance().getStringArray(ResString.RES_WITH_US_IMG_NAME);
        String[] name = ResString.getInstance().getStringArray(ResString.RES_WITH_US_NAME);
        String[] position = ResString.getInstance().getStringArray(ResString.RES_WITH_US_POSITION);
        String[] email = ResString.getInstance().getStringArray(ResString.RES_WITH_US_EMAIL);
        ArrayList<We> weList = new ArrayList<>();
        for (int i = 0; i < imgName.length; i++) {
            We we = new We(imgName[i],name[i],position[i],email[i]);
            weList.add(we);
        }

        mAdapter = new WithUsAdapter(getApplicationContext(), weList);
        mLvList.setAdapter(mAdapter);

    }

    private void settingListener() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
