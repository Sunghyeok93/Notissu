package com.notissu.Fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.notissu.Adapter.NoticeAdapter;
import com.notissu.Dialog.RssItemDialog;
import com.notissu.Model.RssItem;
import com.notissu.R;
import com.notissu.SyncAdapter.NoticeProvider;
import com.notissu.SyncAdapter.NoticeProviderImpl;
import com.notissu.SyncAdapter.RssDatabase;

import java.util.ArrayList;

public class NoticeListFragment extends Fragment {
    private static final String KEY_NOTICE_ROWS = "KEY_NOTICE_ROWS";

    ListView noticeList;
    NoticeAdapter noticeAdapter;
    View rootView;

    public static Fragment newInstance(ArrayList<RssItem> noticeRows) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(KEY_NOTICE_ROWS,noticeRows);
        Fragment fragment = new NoticeListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_notice_list, container, false);
        // Inflate the layout for this fragment

        initWidget();
        settingWidget();
        settingListener();

        return rootView;
    }

    private void initWidget() {
        noticeList = (ListView) rootView.findViewById(R.id.notice_list);
    }

    private void settingWidget() {

        Bundle bundle = getArguments();
        //ListView에 집어넣을 데이터 List
        ArrayList<RssItem> noticeList = bundle.getParcelableArrayList(KEY_NOTICE_ROWS);
        RssDatabase rssDatabase = RssDatabase.getInstance();
        //즐겨찾기에 추가된 List
        ArrayList<RssItem> starredList = new ArrayList<>(rssDatabase.getStarred());
        noticeAdapter = new NoticeAdapter(getContext(),noticeList, starredList);
        this.noticeList.setAdapter(noticeAdapter);
    }

    private void settingListener() {
        noticeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FragmentManager manager = getFragmentManager();
                RssItem rssitem = noticeAdapter.getItem(i);
                DialogFragment mydialog = RssItemDialog.newInstance(rssitem);
                mydialog.show(manager,"");
            }

        });
    }

}
