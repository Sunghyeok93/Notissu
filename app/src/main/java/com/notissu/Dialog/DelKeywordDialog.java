package com.notissu.Dialog;


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

public class DelKeywordDialog extends DialogFragment {
    public static final String DEL_KEY_KEYWORD = "1";
    public static final String NO_DEL_KEY_KEYWORD = "0";
    View rootView;
    Button btnYes;
    Button btnNo;

    OnAddKeywordListner onDelKeywordListner;

    public static DelKeywordDialog newInstance() {
        Bundle args = new Bundle();
        DelKeywordDialog fragment = new DelKeywordDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().setTitle("키워드 삭제");

        rootView = inflater.inflate(R.layout.dialog_delkeyword, null, false);

        btnYes = (Button) rootView.findViewById(R.id.button_keyword_Yes);
        btnNo = (Button) rootView.findViewById(R.id.button_keyword_No);


        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(DEL_KEY_KEYWORD,1);
                onDelKeywordListner.onDel(bundle);
                dismiss();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            Bundle bundle = new Bundle();
            bundle.putInt(NO_DEL_KEY_KEYWORD,0);
            onDelKeywordListner.onDel(bundle);
            dismiss();
        }
    });
        return rootView;

    }

    public void setOnDelKeywordListner(OnAddKeywordListner onDelKeywordListner) {
        this.onDelKeywordListner = onDelKeywordListner;
    }

    public interface OnAddKeywordListner {
        void onDel(Bundle bundle);
    }
}