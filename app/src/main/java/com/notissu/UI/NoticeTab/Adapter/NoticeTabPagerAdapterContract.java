package com.notissu.UI.NoticeTab.Adapter;

/**
 * Created by forhack on 2017-01-01.
 */

public interface NoticeTabPagerAdapterContract {
    interface View {
    }

    interface Model {
        void setData(String title, int flag, int tabCount);
    }

}
