package com.notissu.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.notissu.Database.MainProvider;
import com.notissu.Database.MainProviderImp;
import com.notissu.NoticeList.View.NoticeListFragment;
import com.notissu.Model.RssItem;

import java.util.ArrayList;

import static com.notissu.Util.LogUtils.makeLogTag;

/**
 * Created by forhack on 2016-10-21.
 */

public class NoticeFragmentPagerAdapter extends FragmentStatePagerAdapter{
    private static final String TAG = makeLogTag(NoticeFragmentPagerAdapter.class);
    private String mTitle;
    private int tabCount;
    private int flag;


    public NoticeFragmentPagerAdapter(FragmentManager fm, int flag, String title, int tabCount) {
        super(fm);
        this.mTitle = title;
        this.tabCount = tabCount;
        this.flag = flag;
    }

    @Override
    public Fragment getItem(int position) {
        return NoticeListFragment.newInstance(flag, mTitle, position);
    }


    @Override
    public int getCount() {
        return tabCount;
    }
}
