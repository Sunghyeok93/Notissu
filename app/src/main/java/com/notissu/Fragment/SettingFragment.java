package com.notissu.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.notissu.AboutLibraries.AboutLibrariesActivity;
import com.notissu.Activity.DeleteKeywordActivity;
import com.notissu.Activity.MainActivity;
import com.notissu.Activity.UpdatePeriodActivity;
import com.notissu.R;
import com.notissu.Support.SupportActivity;
import com.notissu.Util.LogUtils;
import com.notissu.Util.ResString;
import com.notissu.WithUs.WithUsActivity;

/**
 * Created by Sunghyeok on 2016-12-04.
 */

public class SettingFragment extends PreferenceFragmentCompat {
    private static final String TAG = LogUtils.makeLogTag(SettingFragment.class);
    private static final String KEY_TITLE = "KEY_TITLE";

    public static final String KEY_DELETE_KEYWORD_TITLE = "KEY_DELETE_KEYWORD_TITLE";
    public static final String KEY_UPDATE_PERIOD_TITLE = "KEY_UPDATE_PERIOD_TITLE";
    public static final String KEY_WITH_US_TITLE = "KEY_WITH_US_TITLE";
    public static final String KEY_ABOUT_LIBRARIES_TITLE = "KEY_ABOUT_LIBRARIES_TITLE";
    public static final String KEY_SUPPORT_TITLE = "KEY_SUPPORT_TITLE";

    private static final String CONTENT_UPDATE_PERIOD;
    private static final String CONTENT_DELETE_KEYWORD;
    private static final String CONTENT_WITH_US;
    private static final String CONTENT_ABOUT_LIBRARIES;
    private static final String CONTENT_SUPPORT;

    private static final String[] mSettingList;
    static {
        mSettingList = ResString.getInstance().getStringArray(ResString.RES_SETTINGS_LIST);
        CONTENT_UPDATE_PERIOD = mSettingList[0];
        CONTENT_DELETE_KEYWORD = mSettingList[1];
        CONTENT_WITH_US = mSettingList[2];
        CONTENT_ABOUT_LIBRARIES = mSettingList[3];
        CONTENT_SUPPORT = mSettingList[4];
    }

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
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.setting_preference);
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_setting,container, false);

        initWidget();
        settingWidget();
        settingListener();

        return rootView;
    }*/

    private void initWidget() {
        mListView = (ListView) rootView.findViewById(R.id.setting_list) ;
    }

    private void settingWidget() {
        //타이틀 설정
        String title = getArguments().getString(KEY_TITLE);
        getActivity().setTitle(title);


        mAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, mSettingList) ;

        ((MainActivity) getActivity()).hideFloatingActionButton();
        mListView.setDividerHeight(2);
        mListView.setAdapter(mAdapter) ;
    }

    private void settingListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                String title = (String) parent.getItemAtPosition(position) ;
                if (title.equals(CONTENT_UPDATE_PERIOD)) {
                    goUpdatePeriod(title);
                } else if (title.equals(CONTENT_DELETE_KEYWORD)) {
                    goDeleteKeyword(title);
                } else if (title.equals(CONTENT_WITH_US)) {
                    goWithUs(title);
                } else if (title.equals(CONTENT_ABOUT_LIBRARIES)) {
                    goAboutLibraries(title);
                } else if (title.equals(CONTENT_SUPPORT)) {
                    goSupport(title);
                }
            }
        }) ;
    }



    public void goUpdatePeriod(String title){
        Intent intent = new Intent(getContext(), UpdatePeriodActivity.class);
        intent.putExtra(KEY_UPDATE_PERIOD_TITLE,title);
        startActivity(intent);
    }

    public void goDeleteKeyword(String title){
        Intent intent = new Intent(getContext(), DeleteKeywordActivity.class);
        intent.putExtra(KEY_DELETE_KEYWORD_TITLE,title);
        startActivity(intent);
    }

    public void goWithUs(String title){
        Intent intent = new Intent(getContext(), WithUsActivity.class);
        intent.putExtra(KEY_WITH_US_TITLE,title);
        startActivity(intent);
    }

    private void goAboutLibraries(String title) {
        Intent intent = new Intent(getContext(), AboutLibrariesActivity.class);
        intent.putExtra(KEY_ABOUT_LIBRARIES_TITLE,title);
        startActivity(intent);
    }

    private void goSupport(String title) {
        Intent intent = new Intent(getContext(), SupportActivity.class);
        intent.putExtra(KEY_SUPPORT_TITLE,title);
        startActivity(intent);
    }
}

