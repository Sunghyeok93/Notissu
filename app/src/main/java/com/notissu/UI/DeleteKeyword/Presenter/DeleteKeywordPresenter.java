package com.notissu.UI.DeleteKeyword.Presenter;

import android.support.annotation.NonNull;
import android.view.Menu;

import com.notissu.UI.DeleteKeyword.Adapter.DeleteKeywordAdapterContract;
import com.notissu.Model.NavigationMenu;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by forhack on 2016-12-28.
 */

public class DeleteKeywordPresenter implements DeleteKeywordContract.Presenter {
    private DeleteKeywordContract.View view;
    private DeleteKeywordAdapterContract.Model adapterModel;
    private DeleteKeywordAdapterContract.View adapterView;

    public DeleteKeywordPresenter(@NonNull DeleteKeywordContract.View view,
                                  @NonNull DeleteKeywordAdapterContract.Model adapterModel,
                                  @NonNull DeleteKeywordAdapterContract.View adapterView) {
        this.view = checkNotNull(view, "DeleteKeywordContract.View cannot be null");
        this.adapterModel = checkNotNull(adapterModel, "DeleteKeywordAdapterContract.Model cannot be null");
        this.adapterView = checkNotNull(adapterView, "DeleteKeywordAdapterContract.View cannot be null");
        view.setPresenter(this);
    }

    @Override
    public void start() {
        loadKeyword();
    }

    @Override
    public void loadKeyword() {
        /*KeywordProvider mKeywordProvider = new KeywordProviderImp();
        ArrayList<String> keywordListDB = new ArrayList<String>(mKeywordProvider.getKeyword());
        adapterModel.addItems(keywordListDB);
        adapterView.refresh();
        if (adapterModel.getCount() == 0) {
            view.showTextNoKeyword();
        }*/
    }

    @Override
    public void deleteKeyword(int position) {
        final String title = adapterModel.getItem(position);
        //키워드를 지울 때 무엇을 해야할까?
        //1. DB에서 지워져야한다.
        /*KeywordProvider mKeywordProvider = new KeywordProviderImp();
        mKeywordProvider.deleteKeyword(title);*/
        //2. Menu에서 지워져야한다.
        Menu menu = NavigationMenu.getInstance().getKeywordMenu();
        int menuSize = menu.size();
        // 내가 지우고자 하는 키워드의 이름으로 아이템을 찾고 아이디를 받아옴
        for (int i = 0; i < menuSize; i++) {
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


}
