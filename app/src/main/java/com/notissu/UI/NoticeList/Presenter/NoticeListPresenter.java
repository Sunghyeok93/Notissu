package com.notissu.UI.NoticeList.Presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.CheckBox;

import com.notissu.Database.LibraryProviderImp;
import com.notissu.Database.MainProviderImp;
import com.notissu.Database.NoticeProvider;
import com.notissu.Fetcher.NoticeFetcher;
import com.notissu.Model.NavigationMenu;
import com.notissu.Model.Notice;
import com.notissu.R;
import com.notissu.UI.NoticeList.Adapter.NoticeListAdapter;
import com.notissu.UI.NoticeList.Adapter.NoticeListAdapterContract;
import com.notissu.UI.NoticeTab.Presenter.NoticeTabContract;

import java.util.List;

import io.realm.Realm;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.notissu.UI.Main.View.MainActivity.FLAG_KEYWORD;
import static com.notissu.UI.Main.View.MainActivity.FLAG_LIBRARY_NOTICE;
import static com.notissu.UI.Main.View.MainActivity.FLAG_MAIN_NOTICE;
import static com.notissu.UI.Main.View.MainActivity.FLAG_SEARCH;
import static com.notissu.UI.Main.View.MainActivity.FLAG_STARRED;
import static com.notissu.UI.NoticeList.View.NoticeListFragment.KEY_CATEGORY;
import static com.notissu.UI.NoticeList.View.NoticeListFragment.KEY_FLAG;
import static com.notissu.UI.NoticeList.View.NoticeListFragment.KEY_TITLE;

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
        notice.setRead(true);

        //Notice Update
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(notice);
            }
        });

        //Navigation 업데이트
        NavigationMenu navigationMenu = NavigationMenu.getInstance();
        navigationMenu.setMenuNotReadCount();

        //TextView 업데이트
        mAdapterView.setItemRead(itemView);

        //Dialog 시작
        mView.showNotice(notice.getId());

    }

    @Override
    public void addSearch(String query) {
        mView.showSearch(query);
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
            NoticeProvider noticeProvider = null;
            if (isMain()) {
                noticeProvider = new MainProviderImp();
            } else if (isLibrary()) {
                noticeProvider = new LibraryProviderImp();
            }
            noticeProvider.updateAllReadCount();
            //Navigation 업데이트
            NavigationMenu navigationMenu = NavigationMenu.getInstance();
            navigationMenu.setMenuNotReadCount();
            //TextView 업데이트
            for (int i = 0; i < mAdapterModel.getCount(); i++) {
                mAdapterModel.getItem(i).setRead(true);
            }
            mAdapterView.refresh();
        }
    }

    @Override
    public void setAdapter(NoticeListAdapter noticeListAdapter) {
        mAdapterModel = noticeListAdapter;
        mAdapterView = noticeListAdapter;
        mView.setAdapter(noticeListAdapter);
    }

    @Override
    public void fetchNoticeList() {
        if (isMain()) {
            NoticeFetcher noticeFetcher = new NoticeFetcher(onFetchNoticeListListener);
            noticeFetcher.fetchNoticeList(NoticeTabContract.NOTICE_CATEGORY[category], 1);
        } else if (isLibrary()) {

        } else if (isStarred()) {
            fetchStarred();
        } else if (isKeyword()) {

        } else if (isSearch()) {

        }
    }

    private void setList(List<Notice> noticeList) {
        mAdapterModel.setLists(noticeList);
        mAdapterView.refresh();
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
                    realm.where(Notice.class).equalTo("id", notice.getId()).findFirst().deleteFromRealm();
                } else {
                    cb.setChecked(true);
                    notice.setStarred(true);
                    realm.copyToRealmOrUpdate(notice);
                }
            }
        });
    }

    private void fetchStarred() {
        List<Notice> noticeList = mRealm.where(Notice.class).equalTo("isStarred", true).findAll();
        setList(noticeList);
    }

    private NoticeListContract.OnFetchNoticeListListener onFetchNoticeListListener = new NoticeListContract.OnFetchNoticeListListener() {
        @Override
        public void onFetchNoticeList(String response) {
            List<Notice> noticeList = Notice.fromJson(response);
            setList(noticeList);
        }
    };

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
