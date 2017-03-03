package com.notissu.UI.Main.AddKeywordDialog.Presenter;

import android.os.Bundle;

import com.notissu.BasePresenter;
import com.notissu.BaseView;
import com.notissu.Model.Keyword;

/**
 * Created by forhack on 2017-01-01.
 */

public interface AddKeywordContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

    }

    interface OnAddKeywordListner {
        void onAdd(Keyword keyword);
    }
}
