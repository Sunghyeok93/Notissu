package com.notissu.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.notissu.R;

/**
 * Created by Sunghyeok on 2016-12-04.
 */

public class OptionFragment extends Fragment{
    private static final String KEY_STATE_BUNDLE = "OptionFragment";
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_option,container, false);
        return rootView;
    }
}

