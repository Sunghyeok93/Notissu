package com.notissu.DeleteKeyword.Presenter;

import android.content.Context;

import com.notissu.BasePresenter;
import com.notissu.BaseView;
import com.notissu.DeleteKeyword.Adapter.DeleteKeywordAdapter;
import com.notissu.DeleteKeyword.Adapter.DeleteKeywordAdapterContract;

/**
 * Created by forhack on 2016-12-28.
 */

public interface DeleteKeywordContract {
    interface View extends BaseView<Presenter> {

        void showTextNoKeyword();
    }

    interface Presenter extends BasePresenter{

        void loadKeyword();

        void deleteKeyword(int position);

        void deleteKeywordAll();
    }
}
