package com.notissu.UI.NoticeTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.notissu.R;
import com.notissu.Util.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoticeTabFragment extends Fragment implements NoticeTabContract.View {
    private static final String TAG = LogUtils.makeLogTag(NoticeTabFragment.class);
    @BindView(R.id.tab_tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.tab_view_pager)
    ViewPager viewPager;

    private NoticeTabContract.Presenter mPresenter;

    public static NoticeTabFragment newInstance(int flag, String title) {
        Bundle args = new Bundle();
        args.putInt(NoticeTabContract.KEY_FLAG, flag);
        args.putString(NoticeTabContract.KEY_TITLE, title);
        NoticeTabFragment fragment = new NoticeTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notice_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        NoticeTabPagerAdapter noticeTabPagerAdapter = new NoticeTabPagerAdapter(getChildFragmentManager());

        mPresenter = new NoticeTabPresenter(getArguments(), this);
        mPresenter.setAdapter(noticeTabPagerAdapter);

        mPresenter.setTab(NoticeTabContract.NOTICE_CATEGORY);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
    }

    @Override
    public void addTabs(String[] noticeCategory) {
        for (int i = 0; i < noticeCategory.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(noticeCategory[i]));
        }
    }

    @Override
    public int getTabCount() {
        return tabLayout.getTabCount();
    }

    @Override
    public void setAdapter(NoticeTabPagerAdapter noticeTabPagerAdapter) {
        viewPager.setAdapter(noticeTabPagerAdapter);
    }
}
