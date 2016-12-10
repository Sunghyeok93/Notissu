package com.notissu.Fragment;

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

import com.notissu.R;

/**
 * Created by Sunghyeok on 2016-12-04.
 */

public class OptionFragment extends Fragment{
    private static final String KEY_STATE_BUNDLE = "OptionFragment";
    static final String[] LIST_MENU = {"업데이트 주기 설정", "키워드 설정 편집", "ㅎㅎ"} ;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_setting,container, false);

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
                    case "키워드 설정 편집" : Toast.makeText(getContext(), "222", Toast.LENGTH_LONG).show(); break;
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
}

