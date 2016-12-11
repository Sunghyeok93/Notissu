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
import com.notissu.SyncAdapter.RssDatabase;
import com.notissu.Util.LogUtils;

/**
 * Created by Sunghyeok on 2016-12-04.
 */

public class SettingFragment extends Fragment{
    private static final String TAG = LogUtils.makeLogTag(SettingFragment.class);
    static final String[] LIST_MENU = {"업데이트 주기 설정", "키워드 설정 편집", "ㅎㅎ"} ;
    View rootView;
    RssDatabase rssDatabase;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_setting,container, false);
        ((MainActivity) getActivity()).hideFloatingActionButton();
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, LIST_MENU) ;

        ListView listview = (ListView) rootView.findViewById(R.id.setting_list) ;
        listview.setDividerHeight(2);
        listview.setAdapter(adapter) ;

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                String strText = (String) parent.getItemAtPosition(position) ;
                switch (strText){
                    case "업데이트 주기 설정" : setUpdateTime(); break;
                    case "키워드 설정 편집" : setKeyword(); break;
                    case "List 3" : Toast.makeText(getContext(), "미구현", Toast.LENGTH_LONG).show();break;
                }
            }
        }) ;

        return rootView;
    }

    public void setUpdateTime(){
        EditKeywordFragment fragment = new EditKeywordFragment();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment_container, fragment);
        fragmentTransaction.commit();
    }
    public void setKeyword(){
        Intent intent = new Intent(getContext(), DeleteKeywordActivity.class);
        startActivity(intent);
    }
}

