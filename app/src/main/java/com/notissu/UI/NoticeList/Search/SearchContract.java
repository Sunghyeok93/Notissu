package com.notissu.UI.NoticeList.Search;

/**
 * Created by forhack on 2017-01-01.
 */

public interface SearchContract {
    interface View {
        void showTitle(String query);

        void showSearch(String query);
    }

    interface Presenter {

        void start();
    }
}
