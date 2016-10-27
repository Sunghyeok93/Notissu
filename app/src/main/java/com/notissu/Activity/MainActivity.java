package com.notissu.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.notissu.Adapter.NoticeAdapter;
import com.notissu.Adapter.NoticeFragmentPagerAdapter;
import com.notissu.R;
import com.notissu.Util.ResString;
import com.notissu.Util.Str;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ListView noticeList;
    NoticeAdapter noticeAdapter;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*위젯을 초기화하는 함수*/
        initWidget();
        /*초기화한 위젯에 데이터를 처리하는 함수*/
        settingWidget();
        /*위젯에 리스터를 붙여주는 함수*/
        settingListener();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initWidget() {

        /*앱이 실행되면 가장 먼저 호출되어야 할 기능이다.
        resource에서 문자열 읽어들기 편하게 하기위해 만든 Util.
        singleton으로 되어있기에 최초에 context를 넘겨줘야함.*/
        ResString.getInstance().setContext(getApplicationContext());
        /*noticeList = (ListView) findViewById(R.id.notice_list);*/
        tabLayout = (TabLayout) findViewById(R.id.tab_tab_layout);
        viewPager = (ViewPager) findViewById(R.id.tab_view_pager);
    }

    private void settingWidget() {
        /*noticeAdapter = new NoticeAdapter(this);
        noticeList.setAdapter(noticeAdapter);
        noticeAdapter.add("helloworld1");*/

        String[] tabStringList = ResString.getInstance().getStringArray(Str.RES_SSU_NOTICES);

        for (int i=0;i<tabStringList.length;i++) {
            tabLayout.addTab(tabLayout.newTab().setText(tabStringList[i]));
        }

        NoticeFragmentPagerAdapter noticeFragmentPagerAdapter =
                new NoticeFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(noticeFragmentPagerAdapter);

    }

    private void settingListener() {

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
