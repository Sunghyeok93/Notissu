package com.notissu.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.notissu.Database.KeywordProvider;
import com.notissu.Database.KeywordProviderImp;
import com.notissu.Fragment.NoticeListFragment;
import com.notissu.Model.RssItem;
import com.notissu.R;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mToolbar = (Toolbar) findViewById(R.id.activity_search_toolbar);
        setSupportActionBar(mToolbar);

        initWidget();
        settingWidget();
        settingListener();

    }

    private void initWidget() {

    }

    private void settingWidget() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String query = getIntent().getStringExtra(NoticeListFragment.KEY_SEARCH_QUERY);
        mToolbar.setTitle(query);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        KeywordProvider keywordProvider = new KeywordProviderImp();
        ArrayList<RssItem> noticeList = new ArrayList<>(keywordProvider.getKeyword(query));
        Fragment fragment = NoticeListFragment.newInstance(NoticeListFragment.FLAG_KEYWORD, query, noticeList);

        fragmentTransaction.replace(R.id.activity_search, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();

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
