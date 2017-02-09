package com.notissu.UI.Setting.DeleteKeyword;

import com.notissu.BasePresenter;
import com.notissu.BaseView;

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
