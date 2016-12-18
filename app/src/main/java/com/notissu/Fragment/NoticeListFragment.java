package com.notissu.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.notissu.Adapter.NoticeAdapter;
import com.notissu.Database.KeywordProvider;
import com.notissu.Database.KeywordProviderImp;
import com.notissu.Database.LibraryProvider;
import com.notissu.Database.LibraryProviderImp;
import com.notissu.Database.MainProvider;
import com.notissu.Database.MainProviderImp;
import com.notissu.Database.NoticeProvider;
import com.notissu.Database.StarredProvider;
import com.notissu.Database.StarredProviderImp;
import com.notissu.Dialog.RssItemDialog;
import com.notissu.Model.NavigationMenu;
import com.notissu.Model.RssItem;
import com.notissu.R;
import com.notissu.Database.RssDatabase;
import com.notissu.SyncAdapter.SyncAdapter;
import com.notissu.SyncAdapter.SyncUtil;
import com.notissu.Util.LogUtils;

import java.util.ArrayList;

public class NoticeListFragment extends Fragment {
    private static final String TAG = LogUtils.makeLogTag(NoticeListFragment.class);

    private static final String KEY_NOTICE_ROWS = "KEY_NOTICE_ROWS";
    private static final String KEY_TITLE= "KEY_TITLE";
    private static final String KEY_CATEGORY= "KEY_CATEGORY";
    private static final String KEY_FLAG= "KEY_FLAG";

    public static final int FLAG_MAIN_NOTICE = 0;
    public static final int FLAG_LIBRARY_NOTICE = 1;
    public static final int FLAG_STARRED = 2;
    public static final int FLAG_KEYWORD = 3;

    ListView mNoticeList;
    NoticeAdapter mNoticeAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    View mRootView;
    SearchView mSearchView;

    //이 List가 Main인지 Library 인지 starred인지 keyword인지 구별 flag
    int flag;
    String title;
    //main일 때만 사용하는 멤버변수, 어느 카테고리인지 알려준다.
    String category;

    public static Fragment newInstance(int flag, String title, String category, ArrayList<RssItem> noticeRows) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TITLE, title);
        bundle.putString(KEY_CATEGORY, category);
        bundle.putInt(KEY_FLAG,flag);
        bundle.putParcelableArrayList(KEY_NOTICE_ROWS,noticeRows);
        Fragment fragment = new NoticeListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static Fragment newInstance(int flag, String title, ArrayList<RssItem> noticeRows) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TITLE,title);
        bundle.putInt(KEY_FLAG,flag);
        bundle.putParcelableArrayList(KEY_NOTICE_ROWS,noticeRows);
        Fragment fragment = new NoticeListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mRootView = inflater.inflate(R.layout.fragment_notice_list, container, false);
        // Inflate the layout for this fragment

        initWidget();
        settingWidget();
        settingListener();

        return mRootView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        boolean isMain = flag == FLAG_MAIN_NOTICE;
        boolean isLibrary = flag == FLAG_LIBRARY_NOTICE;
        if (isMain || isLibrary) {
            inflater.inflate(R.menu.main,menu);
            mSearchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        }
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_read_all) {
            boolean isMain = flag == FLAG_MAIN_NOTICE;
            boolean isLibrary = flag == FLAG_LIBRARY_NOTICE;
            if (isMain || isLibrary) {
                NoticeProvider noticeProvider = null;
                if (isMain) {
                    noticeProvider = new MainProviderImp();
                } else if (isLibrary) {
                    noticeProvider = new LibraryProviderImp();
                }
                noticeProvider.updateAllReadCount();
                //Navigation 업데이트
                NavigationMenu navigationMenu = NavigationMenu.getInstance();
                navigationMenu.setMenuNotReadCount();
                //TextView 업데이트
                for (int i = 0; i < mNoticeAdapter.getCount(); i++) {
                    mNoticeAdapter.getItem(i).setIsRead(RssItem.READ);
                }
                mNoticeAdapter.notifyDataSetChanged();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void initWidget() {
        mNoticeList = (ListView) mRootView.findViewById(R.id.notice_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.notice_swipe_refresh_layout);
    }

    private void settingWidget() {
        Bundle bundle = getArguments();
        title = bundle.getString(KEY_TITLE);
        getActivity().setTitle(title);
        flag = bundle.getInt(KEY_FLAG);
        category = bundle.getString(KEY_CATEGORY);
        //ListView에 집어넣을 데이터 List
        ArrayList<RssItem> noticeList = bundle.getParcelableArrayList(KEY_NOTICE_ROWS);
        StarredProvider starredProvider = new StarredProviderImp();
        //즐겨찾기에 추가된 List
        ArrayList<RssItem> starredList = new ArrayList<>(starredProvider.getStarred());
        mNoticeAdapter = new NoticeAdapter(getContext(),noticeList, starredList);
        this.mNoticeList.setAdapter(mNoticeAdapter);
    }

    private void settingListener() {
        mNoticeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FragmentManager manager = getFragmentManager();
                //읽음표시로 전환하고
                RssItem rssitem = mNoticeAdapter.getItem(i);
                rssitem.setIsRead(RssItem.READ);
                MainProvider mainProvider = new MainProviderImp();
                LibraryProvider libraryProvider = new LibraryProviderImp();
                //RssItem Update
                //두개를 다 업데이트 함으로써 어떤걸 업데이트할지 결정된다.
                mainProvider.updateNotice(rssitem);
                libraryProvider.updateNotice(rssitem);
                //Navigation 업데이트
                NavigationMenu navigationMenu = NavigationMenu.getInstance();
                navigationMenu.setMenuNotReadCount();
                //TextView 업데이트
                TextView tvSubject = (TextView) view.findViewById(R.id.notice_tv_subject);
                tvSubject.setTextColor(Color.parseColor("#aaaaaa"));
                tvSubject.setTypeface(Typeface.DEFAULT);
                //Dialog 시작
                DialogFragment mydialog = RssItemDialog.newInstance(rssitem);
                mydialog.show(manager,"");
            }

        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

//        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                KeywordProvider keywordProvider = RssDatabase.getInstance();
//                ArrayList<RssItem> noticeList = new ArrayList<>(keywordProvider.getKeyword(s));
//                fragmentTransaction.replace(R.id.main_fragment_container, NoticeListFragment.newInstance(NoticeListFragment.FLAG_KEYWORD, s, noticeList));
//                fragmentTransaction.addToBackStack(null).commit();
//                return false;
//            }
//            @Override
//            public boolean onQueryTextChange(String s) {
//
//                return false;
//            }
//        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(syncFinishedReceiver, new IntentFilter(SyncAdapter.SYNC_FINISHED));
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(syncFinishedReceiver);
    }

    private void refresh() {
        if (flag == FLAG_MAIN_NOTICE || flag == FLAG_LIBRARY_NOTICE) {
            SyncUtil.TriggerRefresh();
        }
        listRefresh();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void listRefresh() {
        StarredProvider starredProvider = new StarredProviderImp();
        ArrayList<RssItem> noticeList = null;
        ArrayList<RssItem> starredList = new ArrayList<>(starredProvider.getStarred());
        if (flag == FLAG_MAIN_NOTICE || flag == FLAG_LIBRARY_NOTICE) {
            if (flag == FLAG_MAIN_NOTICE) {
                MainProvider mainProvider = new MainProviderImp();
                noticeList = new ArrayList<>(mainProvider.getSsuNotice(category));
            } else if (flag == FLAG_LIBRARY_NOTICE) {
                LibraryProvider libraryProvider = new LibraryProviderImp();
                noticeList = new ArrayList<>(libraryProvider.getNotice());
            }
        } else if (flag == FLAG_STARRED || flag == FLAG_KEYWORD) {
            if (flag == FLAG_STARRED) {
                noticeList = starredList;
            } else if (flag == FLAG_KEYWORD) {
                KeywordProvider keywordProvider = new KeywordProviderImp();
                noticeList = new ArrayList<>(keywordProvider.getKeyword(title));
            }
        }
        mNoticeAdapter = new NoticeAdapter(getContext(),noticeList, starredList);
        mNoticeList.setAdapter(mNoticeAdapter);
        mNoticeAdapter.notifyDataSetChanged();
    }

    private BroadcastReceiver syncFinishedReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "BroadcastReceiver");
            listRefresh();
        }
    };
}
