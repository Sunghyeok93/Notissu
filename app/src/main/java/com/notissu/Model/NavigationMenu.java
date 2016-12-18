package com.notissu.Model;

import android.support.design.widget.NavigationView;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.notissu.Database.LibraryProvider;
import com.notissu.Database.MainProvider;
import com.notissu.Database.RssDatabase;
import com.notissu.R;
import com.notissu.Util.TestUtils;

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

    public void setMenuNotReadCount() {
        MainProvider mainProvider = RssDatabase.getInstance();
        LibraryProvider libraryProvider = RssDatabase.getInstance();
        setMainNotReadCount(mainProvider.getNotReadCount(RssItem.MainNotice.TABLE_NAME));
        setLibraryNotReadCount(libraryProvider.getNotReadCount(RssItem.LibraryNotice.TABLE_NAME));
    }

    public int getNewId() {
        return id++;
    }
}
