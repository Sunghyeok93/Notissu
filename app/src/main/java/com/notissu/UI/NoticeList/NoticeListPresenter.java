package com.notissu.UI.NoticeList;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.CheckBox;

import com.notissu.Network.NoticeFetcher;
import com.notissu.Model.Notice;
import com.notissu.R;
import com.notissu.UI.NoticeTab.NoticeTabContract;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.notissu.UI.Main.MainContract.FLAG_KEYWORD;
import static com.notissu.UI.Main.MainContract.FLAG_LIBRARY_NOTICE;
import static com.notissu.UI.Main.MainContract.FLAG_MAIN_NOTICE;
import static com.notissu.UI.Main.MainContract.FLAG_SEARCH;
import static com.notissu.UI.Main.MainContract.FLAG_STARRED;
import static com.notissu.UI.NoticeList.NoticeListContract.KEY_CATEGORY;
import static com.notissu.UI.NoticeList.NoticeListContract.KEY_FLAG;
import static com.notissu.UI.NoticeList.NoticeListContract.KEY_TITLE;

/**
 * Created by forhack on 2016-12-29.
 */

public class NoticeListPresenter implements NoticeListContract.Presenter {
    private NoticeListContract.View mView;
    private NoticeListAdapterContract.Model mAdapterModel;
    private NoticeListAdapterContract.View mAdapterView;

    private Bundle bundle;

    //이 List가 Main인지 Library 인지 starred인지 keyword인지 구별 flag
    private int flag;
    //main일 때만 사용하는 멤버변수, 어느 카테고리인지 알려준다.
    private int category;
    //Actionbar에 표시될 Title Keyword에서는 검색 키로 활용됨
    private String title;

    private Realm mRealm = Realm.getDefaultInstance();

    public NoticeListPresenter(Bundle bundle, @NonNull NoticeListContract.View view) {
        this.bundle = bundle;
        this.mView = checkNotNull(view, "NoticeListContract.View cannot be null");
        view.setPresenter(this);

        title = bundle.getString(KEY_TITLE);
        flag = bundle.getInt(KEY_FLAG);
        category = bundle.getInt(KEY_CATEGORY);
    }

    @Override
    public void start() {
        //타이틀 설정
        mView.showTitle(title);
    }

    @Override
    public void onItemClick(View itemView, int position) {
        //읽음표시로 변경
        final Notice notice = mAdapterModel.getItem(position);
        //Notice Update
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                notice.setRead(true);
                realm.copyToRealmOrUpdate(notice);
            }
        });

        //TextView 업데이트
        mAdapterView.setItemRead(itemView);

        //Dialog 시작
        mView.showNotice(notice.getId());

    }

    @Override
    public void loadOptionMenu(Menu menu, MenuInflater inflater) {
        if (isMain() || isLibrary()) {
            mView.showOptionMenu(menu, inflater);
        }
    }

    @Override
    public void readAllItem() {
        if (isMain() || isLibrary()) {
            //TextView 업데이트
            mAdapterModel.readAll();
            mAdapterView.refresh();
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(mAdapterModel.getList());
                }
            });
        }
    }

    @Override
    public void setAdapter(NoticeListAdapter noticeListAdapter) {
        mAdapterModel = noticeListAdapter;
        mAdapterView = noticeListAdapter;
        mView.setAdapter(noticeListAdapter);
    }

    @Override
    public void onStarredClick(View view, final Notice notice) {
        final CheckBox cb = (CheckBox) view.findViewById(R.id.notice_cb_star);

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //클릭되고 난 다음이라 isChecked는 체크되는 순간이다.
                if (cb.isChecked()) {
                    cb.setChecked(false);
                    notice.setStarred(false);
                } else {
                    cb.setChecked(true);
                    notice.setStarred(true);
                }
                realm.copyToRealmOrUpdate(notice);
            }
        });
    }

    @Override
    public void fetchNotice(int page) {
        mView.showProgress();
        if (isMain()) {
            NoticeFetcher noticeFetcher = new NoticeFetcher(onFetchNoticeListListener);
            noticeFetcher.fetchNoticeList(NoticeTabContract.NOTICE_CATEGORY[category], page);
        } else if (isLibrary()) {
            NoticeFetcher noticeFetcher = new NoticeFetcher(onFetchNoticeListListener);
            noticeFetcher.fetchNoticeList(NoticeTabContract.NOTICE_SSU_LIBRARY, page);
        } else if (isStarred()) {
            fetchStarred();
        } else if (isKeyword()) {
            NoticeFetcher noticeFetcher = new NoticeFetcher(onFetchSearchListener);
            noticeFetcher.fetchSearchList(title, page);
        } else if (isSearch()) {
            NoticeFetcher noticeFetcher = new NoticeFetcher(onFetchSearchListener);
            noticeFetcher.fetchSearchList(title, page);
        }
    }

    private void fetchStarred() {
        RealmResults<Notice> noticeList = mRealm.where(Notice.class).equalTo("isStarred", true).findAll();
        noticeList = noticeList.sort("date", Sort.DESCENDING);
        mAdapterModel.setList(new ArrayList<Notice>());
        setList(noticeList);
    }

    private NoticeListContract.OnFetchNoticeListListener onFetchNoticeListListener = new NoticeListContract.OnFetchNoticeListListener() {
        @Override
        public void onFetchNoticeList(String response) {
            if (response.equals("{}")) {
                mView.hideProgress();
                return;
            }
            List<Notice> noticeList = Notice.fromJson(response);
            setList(noticeList);
        }
    };

    private NoticeListContract.OnFetchSearchListener onFetchSearchListener = new NoticeListContract.OnFetchSearchListener() {
        @Override
        public void onFetchKeyword(String response) {
            List<Notice> noticeList = Notice.fromHtml(response);
            setList(noticeList);
        }
    };

    private void setList(List<Notice> noticeList) {
        mAdapterModel.addList(noticeList);
        mAdapterView.refresh();
        mView.hideProgress();
    }

    private boolean isMain() {
        return flag == FLAG_MAIN_NOTICE;
    }

    private boolean isLibrary() {
        return flag == FLAG_LIBRARY_NOTICE;
    }

    private boolean isStarred() {
        return flag == FLAG_STARRED;
    }

    private boolean isKeyword() {
        return flag == FLAG_KEYWORD;
    }

    private boolean isSearch() {
        return flag == FLAG_SEARCH;
    }
}
