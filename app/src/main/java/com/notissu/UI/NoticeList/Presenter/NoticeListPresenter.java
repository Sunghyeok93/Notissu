package com.notissu.UI.NoticeList.Presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.notissu.Database.KeywordProvider;
import com.notissu.Database.KeywordProviderImp;
import com.notissu.Database.LibraryProvider;
import com.notissu.Database.LibraryProviderImp;
import com.notissu.Database.MainProvider;
import com.notissu.Database.MainProviderImp;
import com.notissu.Database.NoticeProvider;
import com.notissu.Database.StarredProvider;
import com.notissu.Database.StarredProviderImp;
import com.notissu.Model.NavigationMenu;
import com.notissu.Model.RssItem;
import com.notissu.UI.NoticeList.Adapter.NoticeListAdapterContract;
import com.notissu.SyncAdapter.SyncUtil;

import java.util.ArrayList;

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

public class NoticeListPresenter implements NoticeListContract.Presenter{
    private NoticeListContract.View view;
    private NoticeListAdapterContract.Model adapterModel;
    private NoticeListAdapterContract.View adapterView;

    private Bundle bundle;

    //이 List가 Main인지 Library 인지 starred인지 keyword인지 구별 flag
    private int flag;
    //main일 때만 사용하는 멤버변수, 어느 카테고리인지 알려준다.
    int category;
    //Actionbar에 표시될 Title Keyword에서는 검색 키로 활용됨
    String title;

    public NoticeListPresenter(Bundle bundle,
                               @NonNull NoticeListContract.View view,
                               @NonNull NoticeListAdapterContract.Model adapterModel,
                               @NonNull NoticeListAdapterContract.View adapterView) {
        this.bundle = bundle;
        this.view = checkNotNull(view,"NoticeListContract.View cannot be null");
        this.adapterModel = checkNotNull(adapterModel,"NoticeListAdapterContract.Model cannot be null");
        this.adapterView = checkNotNull(adapterView,"NoticeListAdapterContract.View cannot be null");
        view.setPresenter(this);
    }

    @Override
    public void start() {
        title = bundle.getString(KEY_TITLE);
        flag = bundle.getInt(KEY_FLAG);
        category = bundle.getInt(KEY_CATEGORY);

        //타이틀 설정
        view.showTitle(title);

        refreshList();
    }

    @Override
    public void onItemClick(View itemView, int position) {
        //읽음표시로 변경
        RssItem rssitem = adapterModel.getItem(position);
        rssitem.setIsRead(RssItem.READ);

        MainProvider mainProvider = new MainProviderImp();
        LibraryProvider libraryProvider = new LibraryProviderImp();

        //RssItem Update
        //두개를 다 업데이트 함으로써 어떤걸 업데이트할지 결정된다.
        mainProvider.updateNotice(rssitem);
        libraryProvider.updateNotice(rssitem);

        //Navigation 업데이트
        NavigationMenu navigationMenu = NavigationMenu.getInstance();
        navigationMenu.setMenuNotReadCount();

        //TextView 업데이트
        adapterView.setItemRead(itemView);

        //Dialog 시작
        view.showNotice(rssitem.getTitle(),rssitem.getLink());

    }

    @Override
    public void addSearch(String query) {
        view.showSearch(query);
    }

    @Override
    public void loadOptionMenu(Menu menu, MenuInflater inflater) {
        if (isMain() || isLibrary()) {
            view.showOptionMenu(menu,inflater);
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
            for (int i = 0; i < adapterModel.getCount(); i++) {
                adapterModel.getItem(i).setIsRead(RssItem.READ);
            }
            adapterView.refresh();
        }
    }

    @Override
    public void fetchNotice() {
        if (isMain() || isLibrary()) {
            SyncUtil.TriggerRefresh();
        }
    }

    @Override
    public void refreshList() {
        //어뎁터에 DB에서 가져온 List 장착
        StarredProvider starredProvider = new StarredProviderImp();
        ArrayList<RssItem> starredList = new ArrayList<>(starredProvider.getStarred());
        ArrayList<RssItem> noticeList = null;
        if (isMain()) {
            MainProvider provider = new MainProviderImp();
            String[] categoryList = MainProvider.NOTICE_CATEGORY;
            noticeList = new ArrayList<>(provider.getSsuNotice(categoryList[category]));
        } else if (isLibrary()) {
            LibraryProvider provider = new LibraryProviderImp();
            noticeList = new ArrayList<>(provider.getNotice());
        } else if (isStarred()) {
            StarredProvider provider = new StarredProviderImp();
            noticeList = new ArrayList<>(provider.getStarred());
        } else if (isKeyword() || isSearch()) {
            KeywordProvider provider = new KeywordProviderImp();
            noticeList = new ArrayList<>(provider.getKeyword(title));
        }
        adapterModel.setLists(noticeList,starredList);
        adapterView.refresh();

        //불러오는 중이라면 show Progress
        if (isMain() || isLibrary()) {
            if (adapterModel.getCount() == 0) {
                view.showProgress();
            } else {
                view.hideProgress();
            }
        }

        view.hideRefreshing();
    }

    @Override
    public boolean isMain() {
        return flag == FLAG_MAIN_NOTICE;
    }

    @Override
    public boolean isLibrary() {
        return flag == FLAG_LIBRARY_NOTICE;
    }

    @Override
    public boolean isStarred() {
        return flag == FLAG_STARRED;
    }

    @Override
    public boolean isKeyword() {
        return flag == FLAG_KEYWORD;
    }

    @Override
    public boolean isSearch() {
        return flag == FLAG_SEARCH;
    }
}
