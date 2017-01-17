package com.notissu.UI.AddKeywordDialog.Presenter;

import android.os.Bundle;

import com.notissu.BasePresenter;
import com.notissu.BaseView;

/**
 * Created by forhack on 2017-01-01.
 */

public interface AddKeywordContract {
    interface View extends BaseView<Presenter> {

        void close();

        void enableAdd(Bundle bundle);
    }

    interface Presenter extends BasePresenter {

        void addKeyword(String keyword);
    }
}
