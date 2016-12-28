package com.notissu.DeleteKeyword.Presenter;

import android.view.Menu;

import com.notissu.Database.KeywordProvider;
import com.notissu.Database.KeywordProviderImp;
import com.notissu.DeleteKeyword.Adapter.DeleteKeywordAdapterContract;
import com.notissu.Model.NavigationMenu;

import java.util.ArrayList;

/**
 * Created by forhack on 2016-12-28.
 */

public class DeleteKeywordPresenter implements DeleteKeywordContract.Presenter {
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
            view.showTextNoKeyword();
        }
    }

    @Override
    public void deleteKeyword(int position) {
        final String title = adapterModel.getItem(position);
        //키워드를 지울 때 무엇을 해야할까?
        //1. DB에서 지워져야한다.
        KeywordProvider mKeywordProvider = new KeywordProviderImp();
        mKeywordProvider.deleteKeyword(title);
        //2. Menu에서 지워져야한다.
        Menu menu = NavigationMenu.getInstance().getKeywordMenu();
        int menuSize = menu.size();
        // 내가 지우고자 하는 키워드의 이름으로 아이템을 찾고 아이디를 받아옴
        for(int i=0;i< menuSize;i++) {
            String menuTitle = menu.getItem(i).getTitle().toString();
            if (title.equals(menuTitle) == true) {
                //지워버릴 Item의 아이디 얻어옴
                int itemId = menu.getItem(i).getItemId();
                menu.removeItem(itemId);
                break;
            }
        }
        //3. 화면을 갱신해야한다.
        adapterModel.remove(title);
        adapterView.refresh();
        if (adapterModel.getCount() == 0) {
            view.showTextNoKeyword();
        }
    }

    @Override
    public void deleteKeywordAll() {
        int keywordSize = adapterModel.getCount();
        for (int i = 0; i < keywordSize; i++) {
            deleteKeyword(0);
        }
    }

    @Override
    public void detachView() {
        view = null;
    }
}
