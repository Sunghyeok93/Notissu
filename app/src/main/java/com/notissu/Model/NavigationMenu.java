package com.notissu.Model;

import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.view.Menu;
import android.widget.TextView;

import com.notissu.R;

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
        mTvMainCount = (TextView) menu.getMenu().getItem(0).getSubMenu().getItem(0).getActionView();
        mTvLibraryCount = (TextView) menu.getMenu().getItem(0).getSubMenu().getItem(1).getActionView();
    }

    public Menu getKeywordMenu() {
        return menu.getMenu().getItem(2).getSubMenu();
    }

    public String getFristItemTitle() {
        String title = menu.getMenu().getItem(0).getSubMenu().getItem(0).getTitle().toString();
        return title;
    }

    public void setMainNotReadCount(int count) {
        if (count == 0) {
            mTvMainCount.setText("");
        } else {
            mTvMainCount.setText(count+"");
        }
    }

    public void setLibraryNotReadCount(int count) {
        if (count == 0) {
            mTvLibraryCount.setText("");
        } else {
            mTvLibraryCount.setText(count+"");
        }
    }

    public int getNewId() {
        return id++;
    }
}
