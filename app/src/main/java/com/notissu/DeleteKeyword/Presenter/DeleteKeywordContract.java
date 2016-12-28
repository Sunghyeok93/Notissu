package com.notissu.DeleteKeyword.Presenter;

import com.notissu.DeleteKeyword.Adapter.DeleteKeywordAdapter;
import com.notissu.DeleteKeyword.Adapter.DeleteKeywordAdapterContract;

/**
 * Created by forhack on 2016-12-28.
 */

public interface DeleteKeywordContract {
    interface View {

        void setVisibilityGone();
    }

    interface Presenter {
        void attachView(View view);

        void setAdapterModel(DeleteKeywordAdapterContract.Model adapterModel);

        void setAdapterView(DeleteKeywordAdapterContract.View adapterView);

        void detachView();

        void loadKeyword();


    }
}
