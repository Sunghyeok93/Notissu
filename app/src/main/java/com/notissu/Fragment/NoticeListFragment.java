package com.notissu.Fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.media.NotProvisionedException;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.notissu.Activity.MainActivity;
import com.notissu.Adapter.NoticeAdapter;
import com.notissu.Database.KeywordProvider;
import com.notissu.Database.LibraryProvider;
import com.notissu.Database.MainProvider;
import com.notissu.Database.NoticeProvider;
import com.notissu.Database.StarredProvider;
import com.notissu.Dialog.RssItemDialog;
import com.notissu.Model.NavigationMenu;
import com.notissu.Model.RssItem;
import com.notissu.R;
import com.notissu.Database.RssDatabase;
import com.notissu.SyncAdapter.SyncUtil;

import java.util.ArrayList;

public class NoticeListFragment extends Fragment {
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
        }
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_read_all) {
            NoticeProvider noticeProvider = RssDatabase.getInstance();
            boolean isMain = flag == FLAG_MAIN_NOTICE;
            boolean isLibrary = flag == FLAG_LIBRARY_NOTICE;
            if (isMain || isLibrary) {
                if (isMain) {
                    noticeProvider.updateAllReadCount(RssItem.MainNotice.TABLE_NAME);
                } else if (isLibrary) {
                    noticeProvider.updateAllReadCount(RssItem.LibraryNotice.TABLE_NAME);
                }
                //Navigation 업데이트
                NavigationMenu navigationMenu = NavigationMenu.getInstance();
                navigationMenu.setMainNotReadCount(noticeProvider.getNotReadCount(RssItem.MainNotice.TABLE_NAME));
                navigationMenu.setLibraryNotReadCount(noticeProvider.getNotReadCount(RssItem.LibraryNotice.TABLE_NAME));
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
        StarredProvider starredProvider = RssDatabase.getInstance();
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
                NoticeProvider noticeProvider = RssDatabase.getInstance();
                //RssItem Update
                noticeProvider.updateNotice(rssitem);
                //Navigation 업데이트
                NavigationMenu navigationMenu = NavigationMenu.getInstance();
                navigationMenu.setMainNotReadCount(noticeProvider.getNotReadCount(RssItem.MainNotice.TABLE_NAME));
                navigationMenu.setLibraryNotReadCount(noticeProvider.getNotReadCount(RssItem.LibraryNotice.TABLE_NAME));
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
    }

    private void refresh() {
        StarredProvider starredProvider = RssDatabase.getInstance();
        ArrayList<RssItem> noticeList = null;
        ArrayList<RssItem> starredList = new ArrayList<>(starredProvider.getStarred());
        if (flag == FLAG_MAIN_NOTICE || flag == FLAG_LIBRARY_NOTICE) {
            SyncUtil.TriggerRefresh();
            if (flag == FLAG_MAIN_NOTICE) {
                MainProvider mainProvider = RssDatabase.getInstance();
                noticeList = new ArrayList<>(mainProvider.getSsuNotice(category));
            } else if (flag == FLAG_LIBRARY_NOTICE) {
                LibraryProvider libraryProvider = RssDatabase.getInstance();
                noticeList = new ArrayList<>(libraryProvider.getLibraryNotice());
            }
        } else if (flag == FLAG_STARRED || flag == FLAG_KEYWORD) {
            if (flag == FLAG_STARRED) {
                noticeList = starredList;
            } else if (flag == FLAG_KEYWORD) {
                KeywordProvider keywordProvider = RssDatabase.getInstance();
                noticeList = new ArrayList<>(keywordProvider.getKeyword(title));
            }
        }
        mNoticeAdapter = new NoticeAdapter(getContext(),noticeList, starredList);
        mNoticeList.setAdapter(mNoticeAdapter);
        mNoticeAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
