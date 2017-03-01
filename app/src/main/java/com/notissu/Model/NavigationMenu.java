package com.notissu.Model;

import android.support.design.widget.NavigationView;
import android.view.Menu;

import com.notissu.Network.KeywordNetwork;
import com.notissu.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by forhack on 2016-12-11.
 * MainActivity와 DeleteKeywordActivity 에서 Menu를 사용해야해서 따로 클래스로 빼두었다.
 */

public class NavigationMenu {
    private static NavigationMenu navigationMenu = new NavigationMenu();
    private NavigationView menu;

    private List<Keyword> keywordList = new ArrayList<>();

    private NavigationMenu() {
    }

    private int id;

    public static NavigationMenu getInstance() {
        return navigationMenu;
    }

    public void setMenu(NavigationView menu) {
        this.menu = menu;
        this.menu.inflateMenu(R.menu.activity_main_drawer);
        KeywordNetwork fetcher = new KeywordNetwork(onFetchKeywordListener);
        fetcher.fetchKeywordList();
    }

    public Menu getKeywordMenu() {
        return menu.getMenu().getItem(2).getSubMenu();
    }

    public String getFristItemTitle() {
        String title = menu.getMenu().getItem(0).getSubMenu().getItem(0).getTitle().toString();
        return title;
    }

    public int getNewId() {
        return id++;
    }

    public void removeKeywordAll() {
        Menu menu = getKeywordMenu();
        int menuSize = menu.size();
        for (int i = 0; i < menuSize; i++) {
            int itemId = menu.getItem(0).getItemId();
            menu.removeItem(itemId);
        }
    }

    private OnFetchKeywordListener onFetchKeywordListener = new OnFetchKeywordListener() {
        @Override
        public void onFetchKeyword(String response) {
            List<Keyword> keywordList = Keyword.fromJson(response);
            addKeywordAll(keywordList);
        }
    };

    public void addKeywordAll(List<Keyword> keywordList) {
        for (int i = 0; i < keywordList.size(); i++) {
            addKeyword(keywordList.get(i));
        }
    }

    public void addKeyword(Keyword keyword) {
        getKeywordMenu().add(R.id.group_keyword, getNewId(), 1, keyword.getTitle()).setIcon(R.drawable.ic_menu_send);
        keywordList.add(keyword);
    }

    public List<Keyword> getKeywordList() {
        return keywordList;
    }

    public void deleteKeyword(Keyword keyword) {
        Menu menu = getKeywordMenu();
        int menuSize = menu.size();
        // 내가 지우고자 하는 키워드의 이름으로 아이템을 찾고 아이디를 받아옴
        for (int i = 0; i < menuSize; i++) {
            String menuTitle = menu.getItem(i).getTitle().toString();
            if (keyword.getTitle().equals(menuTitle) == true) {
                //지워버릴 Item의 아이디 얻어옴
                int itemId = menu.getItem(i).getItemId();
                menu.removeItem(itemId);
                break;
            }
        }
        keywordList.remove(keyword);
    }

    public interface OnFetchKeywordListener {
        void onFetchKeyword(String response);
    }

}
