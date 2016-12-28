package com.notissu.DeleteKeyword.Presenter;

import com.notissu.Database.KeywordProvider;
import com.notissu.Database.KeywordProviderImp;
import com.notissu.DeleteKeyword.Adapter.DeleteKeywordAdapterContract;
import com.notissu.DeleteKeyword.Presenter.DeleteKeywordContract;

import java.util.ArrayList;

/**
 * Created by forhack on 2016-12-28.
 */

public class DeleteKeywordPresenterImpl implements DeleteKeywordContract.Presenter {
    private DeleteKeywordContract.View view;
    private DeleteKeywordAdapterContract.Model adapterModel;
    private DeleteKeywordAdapterContract.View adapterView;


    @Override
    public void attachView(DeleteKeywordContract.View view) {
        this.view = view;
    }

    @Override
    public void setAdapterView(DeleteKeywordAdapterContract.View adapterView) {
        this.adapterView = adapterView;
    }

    @Override
    public void setAdapterModel(DeleteKeywordAdapterContract.Model adapterModel) {
        this.adapterModel = adapterModel;
    }

    @Override
    public void loadKeyword() {
        KeywordProvider mKeywordProvider = new KeywordProviderImp();
        ArrayList<String> keywordListDB = new ArrayList<String>(mKeywordProvider.getKeyword());
        adapterModel.addItems(keywordListDB);
        adapterView.refresh();
        if (adapterModel.getCount() == 0) {
            view.setVisibilityGone();
        }
    }

    @Override
    public void detachView() {
        view = null;
    }
}
