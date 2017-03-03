package com.notissu.UI.NoticeTab;

/**
 * Created by forhack on 2017-01-01.
 */

public interface NoticeTabPagerAdapterContract {
    interface View {
        void refresh();
    }

    interface Model {
        void setData(String title, int flag, int tabCount);
    }

}
