package com.notissu.AddKeywordDialog.View;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.notissu.AddKeywordDialog.Presenter.AddKeywordContract;
import com.notissu.AddKeywordDialog.Presenter.AddKeywordPresenter;
import com.notissu.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sunghyeok on 2016-11-03.
 */

public class AddKeywordDialog extends DialogFragment implements AddKeywordContract.View {
    public static final String KEY_KEYWORD = "FLAG_KEYWORD";
    @BindView(R.id.add_keyword_btn_add)
    Button btnKeyword;
    @BindView(R.id.add_keyword_et_input)
    EditText etText;

    AddKeywordDialog.OnAddKeywordListner onAddKeywordListner;

    private AddKeywordContract.Presenter presenter;

    @Override
    public void setPresenter(AddKeywordContract.Presenter presenter) {
        this.presenter = presenter;
    }

    public static AddKeywordDialog newInstance() {
        Bundle args = new Bundle();
        AddKeywordDialog fragment = new AddKeywordDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_keyword, null, false);
        getDialog().setTitle("키워드 추가");
        ButterKnife.bind(this, view);

        presenter = new AddKeywordPresenter(this);
        presenter.start();

        btnKeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = etText.getText().toString();
                presenter.addKeyword(keyword);
            }
        });

        return view;
    }

    public void setOnAddKeywordListner(OnAddKeywordListner onAddKeywordListner) {
        this.onAddKeywordListner = onAddKeywordListner;
    }

    @Override
    public void close() {
        dismiss();
    }

    @Override
    public void enableAdd(Bundle bundle) {
        onAddKeywordListner.onAdd(bundle);
    }

    public interface OnAddKeywordListner {
        void onAdd(Bundle bundle);
    }
}