package com.notissu.Adapter;

import android.content.Context;
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
    private String[] tabStringList;


    public NoticeFragmentPagerAdapter(FragmentManager fm, String title, int tabCount, String[] tabStringList) {
        super(fm);
        this.mTitle = title;
        this.tabCount = tabCount;
        this.tabStringList = tabStringList;
    }

    @Override
    public Fragment getItem(int position) {
        ArrayList<RssItem> noticeRows = new ArrayList<>(getSsuNotice(tabStringList[position]));
        return NoticeListFragment.newInstance(mTitle, noticeRows);
    }

    public List<RssItem> getSsuNotice(String category) {
        NoticeProvider noticeProvider = new NoticeProviderImpl();
        noticeProvider.getMainNotice(NoticeProvider.NOTICE_SSU_ALL);
        switch (category) {
            case "전체":
                return noticeProvider.getMainNotice(NoticeProvider.NOTICE_SSU_ALL);
            case "학사":
                return noticeProvider.getMainNotice(NoticeProvider.NOTICE_SSU_HACKSA);
            case "장학":
                return noticeProvider.getMainNotice(NoticeProvider.NOTICE_SSU_JANGHACK);
            case "국제교류":
                return noticeProvider.getMainNotice(NoticeProvider.NOTICE_SSU_KUCKJE);
            case "모집,채용":
                return noticeProvider.getMainNotice(NoticeProvider.NOTICE_SSU_MOJIP);
            case "교내행사":
                return noticeProvider.getMainNotice(NoticeProvider.NOTICE_SSU_KYONE);
            case "교외행사":
                return noticeProvider.getMainNotice(NoticeProvider.NOTICE_SSU_KYOWAE);
            case "봉사":
                return noticeProvider.getMainNotice(NoticeProvider.NOTICE_SSU_BONGSA);
            default:
                return noticeProvider.getMainNotice(NoticeProvider.NOTICE_SSU_ALL);
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
