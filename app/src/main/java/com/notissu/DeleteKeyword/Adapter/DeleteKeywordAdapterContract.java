package com.notissu.DeleteKeyword.Adapter;

import java.util.ArrayList;

/**
 * Created by forhack on 2016-12-28.
 */

public interface DeleteKeywordAdapterContract {
    interface Model {
        int getCount();

        String getItem(int i);

        void remove(String string);

        void addItems(ArrayList<String> items);
    }

    interface View {
        void refresh();
    }

}
