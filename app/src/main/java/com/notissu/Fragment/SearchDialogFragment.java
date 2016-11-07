package com.notissu.Fragment;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.notissu.R;

/**
 * Created by Sunghyeok on 2016-11-03.
 */

public class SearchDialogFragment extends DialogFragment implements View.OnClickListener{
    Button search_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().setTitle("검색하기");

        View view = inflater.inflate(R.layout.fragment_dialog_search, null, false);

        search_button = (Button) view.findViewById(R.id.button_search);

        search_button.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {

    }
}