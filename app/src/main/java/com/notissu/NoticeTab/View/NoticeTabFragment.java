package com.notissu.NoticeTab.View;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.notissu.Database.MainProvider;
import com.notissu.NoticeTab.Adapter.NoticeTabPagerAdapter;
import com.notissu.NoticeTab.Presenter.NoticeTabContract;
import com.notissu.NoticeTab.Presenter.NoticeTabPresenter;
import com.notissu.R;
import com.notissu.Util.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoticeTabFragment extends Fragment implements NoticeTabContract.View{
    private static final String TAG= LogUtils.makeLogTag(NoticeTabFragment.class);
    public static final String KEY_TITLE= "KEY_TITLE";
    public static final String KEY_FLAG= "KEY_FLAG";
    @BindView(R.id.tab_tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.tab_view_pager)
    ViewPager viewPager;

    NoticeTabContract.Presenter presenter;

    public static NoticeTabFragment newInstance(int flag, String title) {
        Bundle args = new Bundle();
        args.putInt(KEY_FLAG,flag);
        args.putString(KEY_TITLE,title);
        NoticeTabFragment fragment = new NoticeTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notice_tab, container, false);
        ButterKnife.bind(this, view);

        NoticeTabPagerAdapter noticeTabPagerAdapter = new NoticeTabPagerAdapter(getChildFragmentManager());

        presenter = new NoticeTabPresenter(getArguments(),this, noticeTabPagerAdapter, noticeTabPagerAdapter);
        presenter.start();

        viewPager.setAdapter(noticeTabPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }

    @Override
    public void setPresenter(NoticeTabContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void addTabs() {
        String[] categoryList = MainProvider.NOTICE_CATEGORY;
        for (int i = 0; i<categoryList.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(categoryList[i]));
        }
    }

    @Override
    public int getTabCount() {
        return tabLayout.getTabCount();
    }
}
