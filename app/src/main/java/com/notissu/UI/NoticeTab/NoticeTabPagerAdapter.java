package com.notissu.UI.NoticeTab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.notissu.UI.NoticeList.NoticeListFragment;

import static com.notissu.Util.LogUtils.makeLogTag;

/**
 * Created by forhack on 2016-10-21.
 */

public class NoticeTabPagerAdapter extends FragmentStatePagerAdapter
        implements NoticeTabPagerAdapterContract.Model, NoticeTabPagerAdapterContract.View {
    private static final String TAG = makeLogTag(NoticeTabPagerAdapter.class);
    private String mTitle;
    private int tabCount;
    private int flag;

    public NoticeTabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return NoticeListFragment.newInstance(flag, mTitle, position);
    }

    @Override
    public void setData(String title, int flag, int tabCount) {
        this.mTitle = title;
        this.flag = flag;
        this.tabCount = tabCount;
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    @Override
    public void refresh() {
        notifyDataSetChanged();
    }
}
