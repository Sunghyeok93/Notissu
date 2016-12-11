package com.notissu.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.notissu.Dialog.AddKeywordDialog;
import com.notissu.Fragment.NoticeListFragment;
import com.notissu.Fragment.NoticeTabFragment;
import com.notissu.Fragment.SettingFragment;
import com.notissu.Model.RssItem;
import com.notissu.Notification.Alarm;
import com.notissu.R;
import com.notissu.SyncAdapter.NoticeProvider;
import com.notissu.SyncAdapter.NoticeProviderImpl;
import com.notissu.SyncAdapter.RssDatabase;
import com.notissu.Util.ResString;

import java.util.ArrayList;
import java.util.List;

//메인 화면이 나오는 Activity
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    Toolbar toolbar;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*위젯을 초기화하는 함수*/
        initWidget();
        /*초기화한 위젯에 데이터를 처리하는 함수*/
        settingWidget();
        /*위젯에 리스터를 붙여주는 함수*/
        settingListener();

        Alarm alarm = new Alarm(this);
        alarm.popString("ㅎㅎ");

    }

    private void initWidget() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        fab = (FloatingActionButton) findViewById(R.id.fab);

    }

    private void settingWidget() {

        toggle.syncState();
        drawKeyword();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_fragment_container,NoticeTabFragment.newInstance()).commit();
    }

    private void settingListener() {
        drawer.setDrawerListener(toggle);
        navigationView.setNavigationItemSelectedListener(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddKeywordDialog dialogFragment = AddKeywordDialog.newInstance();
                dialogFragment.setOnAddKeywordListner(new AddKeywordDialog.OnAddKeywordListner() {
                    @Override
                    public void onAdd(Bundle bundle) {
                        String name = bundle.getString(AddKeywordDialog.KEY_KEYWORD);
                        if (name != null)
                            addNewItem(name);
                    }
                });
                dialogFragment.show(getSupportFragmentManager(),"");
            }
        });
    }

    public void hideFloatingActionButton() {
        fab.hide();
    };
    public void showFloatingActionButton() {
        fab.show();
    };

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

    /*
    Actionbar Item을 선택했을 때 생기는 Event 구현
    */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    /*
    Navigation의 메뉴가 클릭 됐을 때 생기는 Event 구현
    */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        fab.show();

        int id = item.getItemId();
        int groupid = item.getGroupId(); // keyword 그룹아이디 가져오기
        String keywordName = (String) item.getTitle(); // keyword 구분을 위한 제목 가져오기

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        NoticeProvider noticeProvider = new NoticeProviderImpl();

        if (id == R.id.nav_ssu_notice) {
            //Main 공지사항
            fragmentTransaction.replace(R.id.main_fragment_container, NoticeTabFragment.newInstance()).commit();
        } else if (id == R.id.nav_ssu_library) {
            //도서관 공지사항
            ArrayList<RssItem> noticeList = new ArrayList<>(noticeProvider.getLibraryNotice());
            fragmentTransaction.replace(R.id.main_fragment_container, NoticeListFragment.newInstance(noticeList)).commit();
        } else if (id == R.id.nav_starred) {
            //즐겨찾기
            ArrayList<RssItem> noticeList = new ArrayList<>(noticeProvider.getStarredNotice());
            fragmentTransaction.replace(R.id.main_fragment_container, NoticeListFragment.newInstance(noticeList)).commit();
        } else if (id == R.id.nav_option) {
            //설정 공지사항
            fragmentTransaction.replace(R.id.main_fragment_container, new SettingFragment()).commit();
        } else if (groupid == R.id.group_keyword) {
            ArrayList<RssItem> noticeList = new ArrayList<>(noticeProvider.getKeywordNotice(keywordName));
            fragmentTransaction.replace(R.id.main_fragment_container, NoticeListFragment.newInstance(noticeList)).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // itemName을 키워드 이름 받아와서 네비게이션에 메뉴 추가
    public boolean addNewItem(String itemName){
        Menu menu = navigationView.getMenu().getItem(2).getSubMenu();
        // 같은 이름의 Keyword를 받았을때 문제 처리해야됨
        int i=0;
        if(menu.size() != 0) {
            for (i=0; i<menu.size();i++) {
                String name = (String) menu.getItem(i).getTitle();
                if (name.equals(itemName) ==  true) {
                    Toast.makeText(getApplicationContext(), "같은 이름의 키워드가 있습니다.", Toast.LENGTH_LONG).show();
                    return false;
                }
            }
        }

        menu.add(R.id.group_keyword, menu.size()+1,1,itemName).setIcon(R.drawable.ic_menu_send);
        RssDatabase rssDatabase = RssDatabase.getInstance();
        rssDatabase.addKeyword(itemName);

        return true;
    }
    // 앱 실행시 데이터베이스에서 키워드 아이템 받아오기
    public void drawKeyword() 
    {
        Menu menu = navigationView.getMenu().getItem(2).getSubMenu();
        RssDatabase rssDatabase = RssDatabase.getInstance();
        List<String> keywordList = rssDatabase.getKeyword();
        if(keywordList.size() != 0) {
            for (int i = 0; i < keywordList.size(); i++) {
                menu.add(R.id.group_keyword, i + 1, 1, keywordList.get(i)).setIcon(R.drawable.ic_menu_send);
            }
        }
    }
}
