package com.notissu.Fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.notissu.Adapter.NoticeAdapter;
import com.notissu.Dialog.RssItemDialog;
import com.notissu.Model.RssItem;
import com.notissu.R;
import com.notissu.SyncAdapter.RssDatabase;

import java.util.ArrayList;

public class NoticeListFragment extends Fragment {
    private static final String KEY_NOTICE_ROWS = "KEY_NOTICE_ROWS";
    private static final String KEY_TITLE= "KEY_TITLE";
    private static final String KEY_FLAG= "KEY_FLAG";

    public static final int KEY_MAIN_NOTICE = 0;
    public static final int KEY_LIBRARY_NOTICE = 1;
    public static final int KEY_STARRED = 2;
    public static final int KEY_KEYWORD= 3;

    ListView mNoticeList;
    NoticeAdapter mNoticeAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    View mRootView;

    //이 List가 현재 어떤 탭에서 실행되고 있는지 구별 flag
    int flag;
    String title;

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
        mRootView = inflater.inflate(R.layout.fragment_notice_list, container, false);
        // Inflate the layout for this fragment

        initWidget();
        settingWidget();
        settingListener();

        return mRootView;
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
        //ListView에 집어넣을 데이터 List
        ArrayList<RssItem> noticeList = bundle.getParcelableArrayList(KEY_NOTICE_ROWS);
        RssDatabase rssDatabase = RssDatabase.getInstance();
        //즐겨찾기에 추가된 List
        ArrayList<RssItem> starredList = new ArrayList<>(rssDatabase.getStarred());
        mNoticeAdapter = new NoticeAdapter(getContext(),noticeList, starredList);
        this.mNoticeList.setAdapter(mNoticeAdapter);
    }

    private void settingListener() {
        mNoticeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FragmentManager manager = getFragmentManager();
                RssItem rssitem = mNoticeAdapter.getItem(i);
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
        if (flag == KEY_MAIN_NOTICE) {

        } else if (flag == KEY_LIBRARY_NOTICE) {

        } else if (flag == KEY_STARRED || flag == KEY_KEYWORD) {
            RssDatabase rssDatabase = RssDatabase.getInstance();
            if (flag == KEY_STARRED) {
                //즐겨찾기에 추가된 List
                ArrayList<RssItem> starredList = new ArrayList<>(rssDatabase.getStarred());
                mNoticeAdapter = new NoticeAdapter(getContext(),starredList, starredList);
            } else if (flag == KEY_KEYWORD) {
                //즐겨찾기에 추가된 List
                ArrayList<RssItem> starredList = new ArrayList<>(rssDatabase.getStarred());
                ArrayList<RssItem> noticeList = new ArrayList<>(rssDatabase.getKeyword(title));
                mNoticeAdapter = new NoticeAdapter(getContext(),noticeList, starredList);
            }
            mNoticeList.setAdapter(mNoticeAdapter);
            mNoticeAdapter.notifyDataSetChanged();
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

}
