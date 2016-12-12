package com.notissu.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.notissu.Fragment.NoticeListFragment;
import com.notissu.Model.RssItem;
import com.notissu.SyncAdapter.NoticeProvider;
import com.notissu.SyncAdapter.NoticeProviderImpl;

import java.util.ArrayList;
import java.util.List;

import static com.notissu.Util.LogUtils.makeLogTag;

/**
 * Created by forhack on 2016-10-21.
 */

public class NoticeFragmentPagerAdapter extends FragmentStatePagerAdapter{
    private static final String TAG = makeLogTag(NoticeFragmentPagerAdapter.class);
    private String mTitle;
    private int tabCount;
    private int flag;
    private String[] categoryList;


    public NoticeFragmentPagerAdapter(FragmentManager fm, int flag, String title, int tabCount, String[] categoryList) {
        super(fm);
        this.mTitle = title;
        this.tabCount = tabCount;
        this.flag = flag;
        this.categoryList = categoryList;
    }

    @Override
    public Fragment getItem(int position) {
        NoticeProvider noticeProvider = new NoticeProviderImpl();
        ArrayList<RssItem> noticeRows = new ArrayList<>(noticeProvider.getSsuNotice(categoryList[position]));
        return NoticeListFragment.newInstance(flag, mTitle, categoryList[position], noticeRows);
    }


    @Override
    public int getCount() {
        return tabCount;
    }
}
