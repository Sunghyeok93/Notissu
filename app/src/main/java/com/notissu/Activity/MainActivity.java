package com.notissu.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.notissu.Database.KeywordProvider;
import com.notissu.Database.KeywordProviderImp;
import com.notissu.Database.LibraryProvider;
import com.notissu.Database.LibraryProviderImp;
import com.notissu.Database.StarredProvider;
import com.notissu.Database.StarredProviderImp;
import com.notissu.Dialog.AddKeywordDialog;
import com.notissu.NoticeList.View.NoticeListFragment;
import com.notissu.NoticeTab.View.NoticeTabFragment;
import com.notissu.Setting.SettingFragment;
import com.notissu.Model.NavigationMenu;
import com.notissu.Model.RssItem;
import com.notissu.Notification.Alarm;
import com.notissu.R;
import com.notissu.Util.LogUtils;

import java.util.ArrayList;
import java.util.List;

//메인 화면이 나오는 Activity
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = LogUtils.makeLogTag(MainActivity.class);

    public static final int FLAG_MAIN_NOTICE = 0;
    public static final int FLAG_LIBRARY_NOTICE = 1;
    public static final int FLAG_STARRED = 2;
    public static final int FLAG_KEYWORD = 3;
    public static final int FLAG_SEARCH = 4;
    public static final int FLAG_SETTING = 5;

    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    Toolbar toolbar;
    FloatingActionButton fab;
    SearchView mSearchView;
    SwipeRefreshLayout refreshLayout;

    LibraryProvider libraryProvider = new LibraryProviderImp();
    KeywordProvider keywordProvider = new KeywordProviderImp();
    StarredProvider starredProvider = new StarredProviderImp();

    //현재 어떤 Fragment가 띄워져 있는지.
    int presentFegment;


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

    }

    private void initWidget() {
        Alarm.cancel(getApplicationContext());
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                NavigationMenu.getInstance().setMenuNotReadCount();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                NavigationMenu.getInstance().setMenuNotReadCount();
            }
        };
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        fab = (FloatingActionButton) findViewById(R.id.fab);

    }

    private void settingWidget() {
        toggle.syncState();
        NavigationMenu navigationMenu = NavigationMenu.getInstance();
        navigationMenu.setMenu(navigationView);
        navigationMenu.setMenuNotReadCount();
        drawKeyword();

        Intent intent = getIntent();
        boolean isAlarm = intent.getBooleanExtra(Alarm.KEY_IS_ALRAM,false);

        if (!isAlarm) {
            presentFegment = FLAG_MAIN_NOTICE;
            String title = NavigationMenu.getInstance().getFristItemTitle();
            showFragment(FLAG_MAIN_NOTICE, NoticeTabFragment.newInstance(FLAG_MAIN_NOTICE, title));
        } else {
            String title = intent.getStringExtra(Alarm.KEY_FIRST_KEYWORD);
            ArrayList<RssItem> noticeList = new ArrayList<>(keywordProvider.getKeyword(title));
            showFragment(FLAG_KEYWORD, NoticeListFragment.newInstance(FLAG_MAIN_NOTICE, title));
        }
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
        LinearLayout easterEgg = (LinearLayout) navigationView.getHeaderView(0).findViewById(R.id.nav_easter_egg);
        easterEgg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "서라야 사랑해♥", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed(){
        DrawerLayout drawer = (DrawerLayout)  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (presentFegment == FLAG_MAIN_NOTICE) {
                super.onBackPressed();
            } else {
                String title = NavigationMenu.getInstance().getFristItemTitle();
                showFragment(FLAG_MAIN_NOTICE,NoticeTabFragment.newInstance(FLAG_MAIN_NOTICE, title));
            }

        }

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
        String itemTitle = item.getTitle().toString(); // keyword 구분을 위한 제목 가져오기

        if (id == R.id.nav_ssu_main) {
            //Main 공지사항
            showFragment(FLAG_MAIN_NOTICE,NoticeTabFragment.newInstance(FLAG_MAIN_NOTICE, itemTitle));
        } else if (id == R.id.nav_ssu_library) {
            //도서관 공지사항
            ArrayList<RssItem> noticeList = new ArrayList<>(libraryProvider.getNotice());
            showFragment(FLAG_LIBRARY_NOTICE,NoticeListFragment.newInstance(FLAG_LIBRARY_NOTICE, itemTitle));
        } else if (id == R.id.nav_starred) {
            //즐겨찾기
            ArrayList<RssItem> noticeList = new ArrayList<>(starredProvider.getStarred());
            showFragment(FLAG_STARRED,NoticeListFragment.newInstance(FLAG_STARRED, itemTitle));
        } else if (groupid == R.id.group_keyword) {
            ArrayList<RssItem> noticeList = new ArrayList<>(keywordProvider.getKeyword(itemTitle));
            showFragment(FLAG_KEYWORD,NoticeListFragment.newInstance(FLAG_KEYWORD, itemTitle));
        } else if (id == R.id.nav_setting) {
            //설정 공지사항
            showFragment(FLAG_SETTING,SettingFragment.newInstance());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showFragment(int flag, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        presentFegment = flag;
        fragmentTransaction.replace(R.id.main_fragment_container, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    // itemName을 키워드 이름 받아와서 네비게이션에 메뉴 추가
    public boolean addNewItem(String itemName){
        NavigationMenu navigationMenu = NavigationMenu.getInstance();
        Menu menu = navigationMenu.getKeywordMenu();
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

        int newId = navigationMenu.getNewId();
        menu.add(R.id.group_keyword, newId,1,itemName).setIcon(R.drawable.ic_menu_send);

//        menu.getItem(newId).setActionView()
        keywordProvider.addKeyword(itemName);

        return true;
    }

    // 앱 실행시 데이터베이스에서 키워드 아이템 받아오기
    public void drawKeyword() {
        NavigationMenu navigationMenu = NavigationMenu.getInstance();
        Menu menu = navigationMenu.getKeywordMenu();
        List<String> keywordList = keywordProvider.getKeyword();
        if (keywordList.size() != 0) {
            for (int i = 0; i < keywordList.size(); i++) {
                menu.add(R.id.group_keyword, navigationMenu.getNewId(), 1, keywordList.get(i)).setIcon(R.drawable.ic_menu_send);
            }
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        getSupportActionBar().setTitle(title);
    }


}
