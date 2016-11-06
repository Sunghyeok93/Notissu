package com.notissu.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.notissu.Adapter.NoticeAdapter;
import com.notissu.R;

public class NoticeListFragment extends Fragment {
    private static final String KEY_ITEM_ARRAY = "KEY_ITEM_ARRAY";
    private static final String KEY_TIME_ARRAY = "KEY_TIME_ARRAY";

    ListView noticeList;
    NoticeAdapter noticeAdapter;
    View rootView;
    String[] itemArray;
    String[] timeArray;

    public static Fragment newInstance(String[] itemArray, String[] timeArray) {
        Bundle bundle = new Bundle();
        //이름 제대로 지정해야한다..
        bundle.putStringArray(KEY_ITEM_ARRAY,itemArray);
        bundle.putStringArray(KEY_TIME_ARRAY,timeArray);
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
        this.timeArray = bundle.getStringArray(KEY_TIME_ARRAY);
        noticeList.setAdapter(noticeAdapter);
        for (int i=0;i<itemArray.length;i++) {
            noticeAdapter.add(itemArray[i]);
            noticeAdapter.addTime(timeArray[i]);
        }




    }

    private void settingListener() {

    }

}
