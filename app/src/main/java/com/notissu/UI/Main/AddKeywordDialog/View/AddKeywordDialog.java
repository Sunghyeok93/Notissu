package com.notissu.UI.Main.AddKeywordDialog.View;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.notissu.Model.Keyword;
import com.notissu.UI.Main.AddKeywordDialog.Presenter.AddKeywordContract;
import com.notissu.UI.Main.AddKeywordDialog.Presenter.AddKeywordPresenter;
import com.notissu.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddKeywordDialog extends DialogFragment implements AddKeywordContract.View {
    @BindView(R.id.add_keyword_et_input)
    EditText mEtText;

    private AddKeywordContract.OnAddKeywordListner mOnAddKeywordListner;

    private AddKeywordContract.Presenter mPresenter;

    @Override
    public void setPresenter(AddKeywordContract.Presenter presenter) {
        this.mPresenter = presenter;
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
        return inflater.inflate(R.layout.dialog_keyword, null, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        getDialog().setTitle("키워드 추가");

        mPresenter = new AddKeywordPresenter(this);
    }

    @OnClick(R.id.add_keyword_btn_add)
    public void onAddClick() {
        Keyword keyword = new Keyword(mEtText.getText().toString());
        mOnAddKeywordListner.onAdd(keyword);
        dismiss();
    }

    public void setOnAddKeywordListner(AddKeywordContract.OnAddKeywordListner onAddKeywordListner) {
        mOnAddKeywordListner = onAddKeywordListner;
    }
}