package com.notissu.Main.Presenter;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.notissu.BasePresenter;
import com.notissu.BaseView;

/**
 * Created by forhack on 2017-01-01.
 */

public interface MainContract {
    interface View extends BaseView<Presenter> {

        void showNavigation();

        void addMenuKeyword(int newId, String keyword);

        void showFragment(int presentFegment, Fragment fragment);

        void showAddKeyword();
    }

    interface Presenter extends BasePresenter {

        void onFabClick();

        void onAddNewItem(String itemName);
    }
}
