package com.notissu.Model;

import android.support.design.widget.NavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.notissu.R;

import java.util.ArrayList;

/**
 * Created by forhack on 2016-12-11.
 * MainActivity와 DeleteKeywordActivity 에서 Menu를 사용해야해서 따로 클래스로 빼두었다.
 */

public class NavigationMenu {
    private static NavigationMenu navigationMenu = new NavigationMenu();
    private NavigationView menu;

    private NavigationMenu() {
    }

    private int id;

    private TextView mTvMainCount;
    private TextView mTvLibraryCount;
    private ArrayList<KeywordPair> mTvKeywordCount = new ArrayList<>();

    public static NavigationMenu getInstance() {
        return navigationMenu;
    }

    public void setMenu(NavigationView menu) {
        this.menu = menu;
        this.menu.inflateMenu(R.menu.activity_main_drawer);

        LinearLayout view = (LinearLayout) menu.getMenu().findItem(R.id.nav_ssu_main).getActionView();
        mTvMainCount = (TextView) view.findViewById(R.id.navigation_item_tv_count);
        view = (LinearLayout) menu.getMenu().findItem(R.id.nav_ssu_library).getActionView();
        mTvLibraryCount = (TextView) view.findViewById(R.id.navigation_item_tv_count);

    }

    public Menu getKeywordMenu() {
        return menu.getMenu().getItem(2).getSubMenu();
    }

    public String getFristItemTitle() {
        String title = menu.getMenu().getItem(0).getSubMenu().getItem(0).getTitle().toString();
        return title;
    }

    private void setMainNotReadCount(int count) {
        if (count == 0) {
            mTvMainCount.setText("");
        } else {
            mTvMainCount.setText(count + "");
        }
    }

    private void setLibraryNotReadCount(int count) {
        if (count == 0) {
            mTvLibraryCount.setText("");
        } else {
            mTvLibraryCount.setText(count + "");
        }
    }

    private void setKeywordTvArray() {
        //menu 목록에 있는 메뉴의 배열 혹은 리스트를 구해서 for문을 돌리자.
        Menu keywordMenu = getKeywordMenu();
        for (int i = 0; i < keywordMenu.size(); i++) {
            MenuItem menuItem = keywordMenu.getItem(i);
            //먼저 Keyword List에 새로운 키워드가 있는지 확인하고,
            KeywordPair menuKeyword = new KeywordPair(menuItem);
            for (int j = 0; j < mTvKeywordCount.size(); j++) {
                KeywordPair listKeyword = mTvKeywordCount.get(j);
                if (!menuKeyword.equals(listKeyword)) {
                    //없을 때만 새롭가 추가한다.
                    mTvKeywordCount.add(menuKeyword);
                }
            }

        }
    }

    private void setKeywodNotReadCount(TextView textView, int count) {
        if (count == 0) {
            textView.setText("");
        } else {
            textView.setText(count + "");
        }
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

    class KeywordPair {
        private String keyword;
        private TextView tvCount;

        public KeywordPair(MenuItem menuItem) {
            keyword = menuItem.getTitle().toString();
            LinearLayout view = (LinearLayout) menuItem.getActionView();
            tvCount = (TextView) view.findViewById(R.id.navigation_item_tv_count);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            KeywordPair that = (KeywordPair) o;

            return keyword != null ? keyword.equals(that.keyword) : that.keyword == null;

        }

        public String getKeyword() {
            return keyword;
        }

        public TextView getTvCount() {
            return tvCount;
        }
    }

}
