package com.notissu.NoticeTab.Adapter;

import com.notissu.Model.RssItem;

import java.util.ArrayList;

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
