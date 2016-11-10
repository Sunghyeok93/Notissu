package com.notissu.Fragment;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.notissu.R;

/**
 * Created by Sunghyeok on 2016-11-03.
 */

public class AddKeywordFragment extends DialogFragment implements View.OnClickListener{
    Button keyword_button;

    public static AddKeywordFragment newInstance() {
        Bundle args = new Bundle();

        AddKeywordFragment fragment = new AddKeywordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().setTitle("키워드 추가");

        View view = inflater.inflate(R.layout.dialog_keyword, null, false);

        keyword_button = (Button) view.findViewById(R.id.button_keyword_set);

        keyword_button.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {

    }
}