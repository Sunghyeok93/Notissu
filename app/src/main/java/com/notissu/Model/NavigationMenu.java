package com.notissu.Model;

import android.support.design.widget.NavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.notissu.Database.KeywordProvider;
import com.notissu.Database.KeywordProviderImp;
import com.notissu.Database.LibraryProvider;
import com.notissu.Database.LibraryProviderImp;
import com.notissu.Database.MainProvider;
import com.notissu.Database.MainProviderImp;
import com.notissu.Database.RssDatabase;
import com.notissu.R;

import java.util.ArrayList;

/**
 * Created by forhack on 2016-12-11.
 * MainActivity와 DeleteKeywordActivity 에서 Menu를 사용해야해서 따로 클래스로 빼두었다.
 */

public class NavigationMenu {
    private static NavigationMenu navigationMenu = new NavigationMenu();
    private NavigationView menu;
    private NavigationMenu() {}
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
            mTvMainCount.setText(count+"");
        }
    }

    private void setLibraryNotReadCount(int count) {
        if (count == 0) {
            mTvLibraryCount.setText("");
        } else {
            mTvLibraryCount.setText(count+"");
        }
    }

    private void setKeywordTvArray() {
        //menu 목록에 있는 메뉴의 배열 혹은 리스트를 구해서 for문을 돌리자.
        Menu keywordMenu = getKeywordMenu();
        for (int i = 0; i < keywordMenu.size(); i++) {
            MenuItem menuItem = keywordMenu.getItem(i);
            //먼저 Keyword List에 새로운 키워드가 있는지 확인하고,
            KeywordPair menuKeyword = new KeywordPair(menuItem);
            for (int j = 0; j <mTvKeywordCount.size(); j++) {
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
            textView.setText(count+"");
        }
    }

    public void setMenuNotReadCount() {
        MainProvider mainProvider = new MainProviderImp();
        LibraryProvider libraryProvider = new LibraryProviderImp();
        KeywordProvider keywordProvider = new KeywordProviderImp();
        setMainNotReadCount(mainProvider.getNotReadCount());
        setLibraryNotReadCount(libraryProvider.getNotReadCount());

        /*키워드 추가 기능은 잠시 멈춰둔다. 동적으로 삽입하는 키워드에 ActionView를 넣지 못한다.*/
        /*//키워드의 변화를 체크해서 Array에 담는다.
        setKeywordTvArray();

        //변화된 Array를 이용해서 Keyword Count를 세팅한다.
        for (int i = 0; i < mTvKeywordCount.size(); i++) {
            KeywordPair keywordPair = mTvKeywordCount.get(i);
            TextView textView = keywordPair.getTvCount();
            setKeywodNotReadCount(textView, keywordProvider.getNotReadCount(keywordPair.getKeyword()));
        }*/

    }

    public int getNewId() {
        return id++;
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
