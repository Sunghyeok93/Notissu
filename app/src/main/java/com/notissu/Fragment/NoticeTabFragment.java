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
import com.notissu.Util.ResString;
import com.notissu.Util.Str;

public class NoticeTabFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    View rootView;
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
        String[] tabStringList = ResString.getInstance().getStringArray(Str.RES_SSU_NOTICES);

        for (int i=0;i<tabStringList.length;i++) {
            tabLayout.addTab(tabLayout.newTab().setText(tabStringList[i]));
        }

        NoticeFragmentPagerAdapter noticeFragmentPagerAdapter =
                new NoticeFragmentPagerAdapter(getFragmentManager(),tabLayout.getTabCount(),tabStringList);
        viewPager.setAdapter(noticeFragmentPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

    private void settingListener() {

    }

}
