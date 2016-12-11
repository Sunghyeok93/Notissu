package com.notissu.Fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.notissu.Adapter.NoticeFragmentPagerAdapter;
import com.notissu.R;
import com.notissu.Util.LogUtils;
import com.notissu.Util.ResString;

public class NoticeTabFragment extends Fragment {
    private static final String TAG= LogUtils.makeLogTag(NoticeTabFragment.class);
    private static final String KEY_TITLE= "KEY_TITLE";
    TabLayout tabLayout;
    ViewPager viewPager;
    View rootView;

    public static NoticeTabFragment newInstance(String title) {
        Bundle args = new Bundle();
        args.putString(KEY_TITLE,title);
        NoticeTabFragment fragment = new NoticeTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_notice_tab, container, false);

        initWidget();
        settingWidget();
        settingListener();
        return rootView;
    }

    private void initWidget() {
        tabLayout = (TabLayout) rootView.findViewById(R.id.tab_tab_layout);
        viewPager = (ViewPager) rootView.findViewById(R.id.tab_view_pager);
    }

    private void settingWidget() {
        //타이틀 변경
        String title = getArguments().getString(KEY_TITLE);

        String[] tabStringList = ResString.getInstance().getStringArray(ResString.RES_SSU_NOTICES);

        for (int i=0;i<tabStringList.length;i++) {
            tabLayout.addTab(tabLayout.newTab().setText(tabStringList[i]));
        }

        NoticeFragmentPagerAdapter noticeFragmentPagerAdapter =
                new NoticeFragmentPagerAdapter(getFragmentManager(), title, tabLayout.getTabCount(), tabStringList);
        viewPager.setAdapter(noticeFragmentPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

    private void settingListener() {
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
    }

}
