package com.notissu.UI.Main.Presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.Menu;

import com.notissu.Database.KeywordProvider;
import com.notissu.Database.KeywordProviderImp;
import com.notissu.Model.NavigationMenu;
import com.notissu.UI.NoticeList.View.NoticeListFragment;
import com.notissu.UI.NoticeTab.View.NoticeTabFragment;
import com.notissu.Notification.Alarm;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.notissu.UI.Main.View.MainActivity.FLAG_KEYWORD;
import static com.notissu.UI.Main.View.MainActivity.FLAG_MAIN_NOTICE;

/**
 * Created by forhack on 2017-01-01.
 */

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View view;
    private Bundle bundle;

    public MainPresenter(Bundle bundle, @NonNull MainContract.View view) {
        if (bundle != null) {
            this.bundle = bundle;
        } else {
            this.bundle = new Bundle();
        }
        this.view = checkNotNull(view);

        view.setPresenter(this);
    }

    @Override
    public void start() {
        view.showNavigation();

        KeywordProvider provider = new KeywordProviderImp();
        List<String> keywordList = provider.getKeyword();
        if (keywordList.size() != 0) {
            for (int i = 0; i < keywordList.size(); i++) {
                view.addMenuKeyword(NavigationMenu.getInstance().getNewId(), keywordList.get(i));
            }
        }

        boolean isAlarm = bundle.getBoolean(Alarm.KEY_IS_ALRAM,false);
        if (!isAlarm) {
            int presentFegment = FLAG_MAIN_NOTICE;
            String title = NavigationMenu.getInstance().getFristItemTitle();
            Fragment fragment = NoticeTabFragment.newInstance(presentFegment, title);
            view.showFragment(presentFegment, fragment);
        } else {
            int presentFegment = FLAG_KEYWORD;
            String title = bundle.getString(Alarm.KEY_FIRST_KEYWORD);
            Fragment fragment = NoticeListFragment.newInstance(presentFegment, title);
            view.showFragment(presentFegment, fragment);
        }
    }

    @Override
    public void onFabClick() {
        view.showAddKeyword();

    }

    @Override
    public void onAddNewItem(String itemName) {
        NavigationMenu navigationMenu = NavigationMenu.getInstance();
        Menu menu = navigationMenu.getKeywordMenu();
        // 같은 이름의 Keyword를 받았을때 문제 처리해야됨
        if(menu.size() != 0) {
            for (int i=0; i<menu.size();i++) {
                String name = (String) menu.getItem(i).getTitle();
                if (name.equals(itemName) ==  true) { // 같은 키워드가 있다.
                    return;
                }
            }
        }

        int newId = navigationMenu.getNewId();
        view.addMenuKeyword(newId, itemName);
        KeywordProvider provider = new KeywordProviderImp();
        provider.addKeyword(itemName);
    }
}
