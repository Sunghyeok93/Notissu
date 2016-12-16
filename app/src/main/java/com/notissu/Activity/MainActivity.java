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
import android.widget.Toast;

import com.notissu.Database.KeywordProvider;
import com.notissu.Database.LibraryProvider;
import com.notissu.Database.StarredProvider;
import com.notissu.Dialog.AddKeywordDialog;
import com.notissu.Fragment.NoticeListFragment;
import com.notissu.Fragment.NoticeTabFragment;
import com.notissu.Fragment.SettingFragment;
import com.notissu.Model.NavigationMenu;
import com.notissu.Model.RssItem;
import com.notissu.Notification.Alarm;
import com.notissu.R;
import com.notissu.Database.NoticeProvider;
import com.notissu.Database.NoticeProviderImpl;
import com.notissu.Database.RssDatabase;

import java.util.ArrayList;
import java.util.List;

//메인 화면이 나오는 Activity
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String STACK_NAME_MAIN = "STACK_NAME_MAIN";
    private static final String STACK_NAME_LIBRARY = "STACK_NAME_LIBRARY";
    private static final String STACK_NAME_STARRED = "STACK_NAME_STARRED";
    private static final String STACK_NAME_KEYWORD = "STACK_NAME_KEYWORD";
    private static final String STACK_NAME_SETTING = "STACK_NAME_SETTING";

    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    Toolbar toolbar;
    FloatingActionButton fab;
    SearchView mSearchView;
    SwipeRefreshLayout refreshLayout;


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
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        fab = (FloatingActionButton) findViewById(R.id.fab);

    }


    private void settingWidget() {
        toggle.syncState();
        NavigationMenu navigationMenu = NavigationMenu.getInstance();
        navigationMenu.setMenu(navigationView);
        drawKeyword();

        Intent intent = getIntent();
        boolean isAlarm = intent.getBooleanExtra(Alarm.KEY_IS_ALRAM,false);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (!isAlarm) {
            String title = NavigationMenu.getInstance().getFristItemTitle();
            fragmentTransaction.replace(R.id.main_fragment_container, NoticeTabFragment.newInstance(NoticeListFragment.KEY_MAIN_NOTICE, title));
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.commit();
        } else {
            KeywordProvider keywordProvider = RssDatabase.getInstance();
            String title = intent.getStringExtra(Alarm.KEY_FIRST_KEYWORD);
            ArrayList<RssItem> noticeList = new ArrayList<>(keywordProvider.getKeyword(title));
            fragmentTransaction.replace(R.id.main_fragment_container, NoticeListFragment.newInstance(NoticeListFragment.KEY_MAIN_NOTICE, title, noticeList));
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.commit();
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
    }

    public void hideFloatingActionButton() {
        fab.hide();
    };
    public void showFloatingActionButton() {
        fab.show();
    };

    @Override
    public void onBackPressed(){
     DrawerLayout drawer = (DrawerLayout)  findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        toolbar.inflateMenu(R.menu.main);
        mSearchView = (SearchView) toolbar.getMenu().findItem(R.id.menu_search).getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                KeywordProvider keywordProvider = RssDatabase.getInstance();
                ArrayList<RssItem> noticeList = new ArrayList<>(keywordProvider.getKeyword(s));
                fragmentTransaction.replace(R.id.main_fragment_container, NoticeListFragment.newInstance(NoticeListFragment.KEY_KEYWORD, s, noticeList));
                fragmentTransaction.addToBackStack(null).commit();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });
        mSearchView.setQueryHint("검색 입력");
        mSearchView.clearFocus();

        return super.onCreateOptionsMenu(menu);
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

        if (id == R.id.nav_ssu_notice) {
            //Main 공지사항
            showFragment(STACK_NAME_MAIN,NoticeTabFragment.newInstance(NoticeListFragment.KEY_MAIN_NOTICE, itemTitle));
        } else if (id == R.id.nav_ssu_library) {
            //도서관 공지사항
            LibraryProvider libraryProvider = RssDatabase.getInstance();
            ArrayList<RssItem> noticeList = new ArrayList<>(libraryProvider.getLibraryNotice());
            showFragment(STACK_NAME_LIBRARY,NoticeListFragment.newInstance(NoticeListFragment.KEY_LIBRARY_NOTICE, itemTitle, noticeList));
        } else if (id == R.id.nav_starred) {
            //즐겨찾기
            StarredProvider starredProvider = RssDatabase.getInstance();
            ArrayList<RssItem> noticeList = new ArrayList<>(starredProvider.getStarred());
            showFragment(STACK_NAME_STARRED,NoticeListFragment.newInstance(NoticeListFragment.KEY_STARRED, itemTitle, noticeList));
        } else if (groupid == R.id.group_keyword) {
            KeywordProvider keywordProvider = RssDatabase.getInstance();
            ArrayList<RssItem> noticeList = new ArrayList<>(keywordProvider.getKeyword(itemTitle));
            showFragment(STACK_NAME_KEYWORD+itemTitle,NoticeListFragment.newInstance(NoticeListFragment.KEY_KEYWORD, itemTitle, noticeList));
        } else if (id == R.id.nav_setting) {
            //설정 공지사항
            showFragment(STACK_NAME_SETTING,SettingFragment.newInstance());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showFragment(String stackName, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentManager.popBackStack(stackName,FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentTransaction.replace(R.id.main_fragment_container, fragment);
        fragmentTransaction.addToBackStack(stackName);
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

        menu.add(R.id.group_keyword, navigationMenu.getNewId(),1,itemName).setIcon(R.drawable.ic_menu_send);
        KeywordProvider keywordProvider = RssDatabase.getInstance();
        keywordProvider.addKeyword(itemName);

        return true;
    }

    // 앱 실행시 데이터베이스에서 키워드 아이템 받아오기
    public void drawKeyword() {
        NavigationMenu navigationMenu = NavigationMenu.getInstance();
        Menu menu = navigationMenu.getKeywordMenu();
        KeywordProvider keywordProvider = RssDatabase.getInstance();
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
