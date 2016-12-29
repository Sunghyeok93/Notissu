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
    private Context mContext;
    private String mTitle;
    private int tabCount;
    private int flag;
    private String[] categoryList;


    public NoticeFragmentPagerAdapter(FragmentManager fm, Context context, int flag, String title, int tabCount, String[] categoryList) {
        super(fm);
        this.mContext = context;
        this.mTitle = title;
        this.tabCount = tabCount;
        this.flag = flag;
        this.categoryList = categoryList;
    }

    @Override
    public Fragment getItem(int position) {
        MainProvider mainProvider = new MainProviderImp();
        ArrayList<RssItem> noticeRows = new ArrayList<>(mainProvider.getSsuNotice(categoryList[position]));
        return NoticeListFragment.newInstance(flag, mTitle, categoryList[position], noticeRows);
    }


    @Override
    public int getCount() {
        return tabCount;
    }
}
