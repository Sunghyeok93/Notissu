package com.notissu.UI.Setting.DeleteKeyword;

import android.support.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessaging;
import com.notissu.Model.Keyword;
import com.notissu.Model.NavigationMenu;
import com.notissu.Network.KeywordNetwork;

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
        final Keyword keyword = mAdapterModel.getItem(position);

        // 서버에 삭제요청보낸다.

        KeywordNetwork sender = new KeywordNetwork();
        sender.deleteKeyword(keyword.getTitle());

        // 구독 취소 한다.
        FirebaseMessaging.getInstance().unsubscribeFromTopic(keyword.getHash());

        //2. Menu에서 지워져야한다.
        NavigationMenu navigationMenu = NavigationMenu.getInstance();
        navigationMenu.deleteKeyword(keyword);

        //3. 화면을 갱신해야한다.
        mAdapterView.refresh();
        if (mAdapterModel.getCount() == 0) {
            mView.showTextNoKeyword();
        }
    }

    @Override
    public void deleteKeywordAll() {

        // 서버에 삭제요청보낸다.
        KeywordNetwork sender = new KeywordNetwork();
        sender.deleteKeywordAll();

        // 구독 취소 한다.
        NavigationMenu navigationMenu = NavigationMenu.getInstance();
        List<Keyword> keywordList = navigationMenu.getKeywordList();
        for (int i = 0; i <keywordList.size(); i++) {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(keywordList.get(i).getHash());
        }

        //2. Menu에서 지워져야한다.
        navigationMenu.deleteKeywordAll();

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
        List<Keyword> keywordList = NavigationMenu.getInstance().getKeywordList();
        mAdapterModel.setData(keywordList);
        mAdapterView.refresh();
        if (mAdapterModel.getCount() == 0) {
            mView.showTextNoKeyword();
        }
    }
}
