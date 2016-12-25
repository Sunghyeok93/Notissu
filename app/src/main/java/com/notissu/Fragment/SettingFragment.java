package com.notissu.Fragment;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.notissu.R;
import com.notissu.Util.LogUtils;
/**
 * Created by Sunghyeok on 2016-12-04.
 */

public class SettingFragment extends PreferenceFragmentCompat {
    private static final String TAG = LogUtils.makeLogTag(SettingFragment.class);
    private static final String TITLE = "설정";

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.empty,menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    public static SettingFragment newInstance() {
        Bundle args = new Bundle();
        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setHasOptionsMenu(true);
        getActivity().setTitle(TITLE);
        addPreferencesFromResource(R.xml.setting_preference);
    }

}

