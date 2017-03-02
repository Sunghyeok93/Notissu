package com.notissu.UI.Setting.DeleteKeyword;

import com.notissu.Model.Keyword;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by forhack on 2016-12-28.
 */

public interface DeleteKeywordAdapterContract {
    interface View {
        void refresh();
    }

    interface Model {
        int getCount();

        Keyword getItem(int i);

        void remove(String string);

        void setData(List<Keyword> data);

        void removeAll();
    }
}
