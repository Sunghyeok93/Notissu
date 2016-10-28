package com.notissu.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.notissu.Adapter.NoticeAdapter;
import com.notissu.R;
import com.notissu.Util.Str;

public class NoticeListFragment extends Fragment {
    private static final String KEY_ITEM_ARRAY = "KEY_ITEM_ARRAY";
    ListView noticeList;
    NoticeAdapter noticeAdapter;
    View rootView;
    String[] itemArray;

    public static Fragment newInstance(String[] itemArray) {
        Bundle bundle = new Bundle();
        //이름 제대로 지정해야한다..
        bundle.putStringArray(KEY_ITEM_ARRAY,itemArray);
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
        noticeAdapter = new NoticeAdapter(getContext());
    }

    private void settingWidget() {
        Bundle bundle = getArguments();
        this.itemArray = bundle.getStringArray(KEY_ITEM_ARRAY);
        
        noticeList.setAdapter(noticeAdapter);
        for (int i=0;i<itemArray.length;i++) {
            noticeAdapter.add(itemArray[i]);
        }



    }

    private void settingListener() {

    }

}
