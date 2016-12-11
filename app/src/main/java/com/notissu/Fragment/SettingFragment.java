package com.notissu.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.notissu.Activity.DeleteKeywordActivity;
import com.notissu.Activity.MainActivity;
import com.notissu.R;
import com.notissu.Util.LogUtils;

/**
 * Created by Sunghyeok on 2016-12-04.
 */

public class SettingFragment extends Fragment{
    private static final String TAG = LogUtils.makeLogTag(SettingFragment.class);
    private static final String KEY_TITLE = "KEY_TITLE";
    public static final String KEY_DELETE_KEYWORD_TITLE = "KEY_DELETE_KEYWORD_TITLE";

    static final String[] LIST_MENU = {"업데이트 주기 설정", "키워드 설정 편집", "ㅎㅎ"} ;
    View rootView;

    ArrayAdapter mAdapter;
    ListView mListView;


    public static SettingFragment newInstance(String title) {
        Bundle args = new Bundle();
        args.putString(KEY_TITLE,title);
        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_setting,container, false);

        initWidget();
        settingWidget();
        settingListener();

        return rootView;
    }

    private void initWidget() {
        mAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, LIST_MENU) ;
        mListView = (ListView) rootView.findViewById(R.id.setting_list) ;
    }

    private void settingWidget() {
        //타이틀 설정
        String title = getArguments().getString(KEY_TITLE);
        getActivity().setTitle(title);

        ((MainActivity) getActivity()).hideFloatingActionButton();
        mListView.setDividerHeight(2);
        mListView.setAdapter(mAdapter) ;
    }

    private void settingListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                String strText = (String) parent.getItemAtPosition(position) ;
                switch (strText){
                    case "업데이트 주기 설정" :
                        setUpdateTime();
                        break;
                    case "키워드 설정 편집" :
                        setKeyword(strText);
                        break;
                    case "List 3" :
                        Toast.makeText(getContext(), "미구현", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        }) ;
    }

    public void setUpdateTime(){
        EditKeywordFragment fragment = new EditKeywordFragment();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment_container, fragment);
        fragmentTransaction.commit();
    }
    public void setKeyword(String title){
        Intent intent = new Intent(getContext(), DeleteKeywordActivity.class);
        intent.putExtra(KEY_DELETE_KEYWORD_TITLE,title);
        startActivity(intent);
    }
}

