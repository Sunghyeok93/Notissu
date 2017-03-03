package com.notissu.UI.Setting.DeleteKeyword;

import com.notissu.BasePresenter;
import com.notissu.BaseView;

/**
 * Created by forhack on 2016-12-28.
 */

public interface DeleteKeywordContract {
    interface View extends BaseView<Presenter> {

        void showTextNoKeyword();

        void setAdapter(DeleteKeywordAdapter deleteKeywordAdapter);
    }

    interface Presenter extends BasePresenter{

        void deleteKeyword(int position);

        void deleteKeywordAll();

        void setAdapter(DeleteKeywordAdapter deleteKeywordAdapter);

        void fetchKeyword();
    }
}
