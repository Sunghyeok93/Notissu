package com.notissu.UI.Setting.DeleteKeyword;

import android.support.annotation.NonNull;
import android.view.Menu;

import com.notissu.Model.Keyword;
import com.notissu.Model.NavigationMenu;

import java.util.List;

import io.realm.Realm;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by forhack on 2016-12-28.
 */

public class DeleteKeywordPresenter implements DeleteKeywordContract.Presenter {
    private DeleteKeywordContract.View mView;
    private DeleteKeywordAdapterContract.Model mAdapterModel;
    private DeleteKeywordAdapterContract.View mAdapterView;

    private Realm mRealm = Realm.getDefaultInstance();

    public DeleteKeywordPresenter(@NonNull DeleteKeywordContract.View view) {
        this.mView = checkNotNull(view, "DeleteKeywordContract.View cannot be null");
        view.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void deleteKeyword(int position) {
        final String title = mAdapterModel.getItem(position).getTitle();
        //키워드를 지울 때 무엇을 해야할까?
        //1. DB에서 지워져야한다.
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Keyword.class).equalTo("title", title).findFirst().deleteFromRealm();
            }
        });

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
        mAdapterView.refresh();
        if (mAdapterModel.getCount() == 0) {
            mView.showTextNoKeyword();
        }
    }

    @Override
    public void deleteKeywordAll() {
        //1. DB에서 지워져야한다.
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Keyword.class).findAll().deleteAllFromRealm();
            }
        });

        //2. Menu에서 지워져야한다.
        NavigationMenu navigationMenu = NavigationMenu.getInstance();
        navigationMenu.removeKeywordAll();

        //3. 화면을 갱신해야한다.
        mView.showTextNoKeyword();
    }

    @Override
    public void setAdapter(DeleteKeywordAdapter deleteKeywordAdapter) {
        mAdapterModel = deleteKeywordAdapter;
        mAdapterView = deleteKeywordAdapter;
        mView.setAdapter(deleteKeywordAdapter);
    }

    @Override
    public void fetchKeyword() {
        List<Keyword> keywordList = mRealm.where(Keyword.class).findAll();
        mAdapterModel.setData(keywordList);
        mAdapterView.refresh();
        if (mAdapterModel.getCount() == 0) {
            mView.showTextNoKeyword();
        }
    }
}
