package com.notissu.UI.Setting.DeleteKeyword;

/**
 * Created by forhack on 2016-12-28.
 */

public interface DeleteKeywordContract {
    interface View {

        void showTextNoKeyword();

        void setAdapter(DeleteKeywordAdapter deleteKeywordAdapter);
    }

    interface Presenter {

        void deleteKeyword(int position);

        void deleteKeywordAll();

        void setAdapter(DeleteKeywordAdapter deleteKeywordAdapter);

        void fetchKeyword();
    }
}
