package com.notissu.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.notissu.Fragment.NoticeListFragment;
import com.notissu.Model.NoticeRow;
import com.notissu.Util.Str;

import java.util.ArrayList;

import static com.notissu.Util.LogUtils.makeLogTag;

/**
 * Created by forhack on 2016-10-21.
 */

public class NoticeFragmentPagerAdapter extends FragmentStatePagerAdapter{
    private static final String TAG = makeLogTag(NoticeFragmentPagerAdapter.class);
    private int tabCount;
    private String[] tabStringList;
    public NoticeFragmentPagerAdapter(FragmentManager fm, int tabCount, String[] tabStringList) {
        super(fm);
        this.tabCount = tabCount;
        this.tabStringList = tabStringList;
    }

    @Override
    public Fragment getItem(int position) {
        ArrayList<NoticeRow> noticeRows = getSsuNotice(tabStringList[position]);

        return NoticeListFragment.newInstance(noticeRows);
    }

    public ArrayList<NoticeRow> getSsuNotice(String category) {
        switch (category) {
            case "전체":
                return Str.ALL_SSU_NOTICES;
            case "학사":
                return Str.HACKSA_SSU_NOTICES;
            case "장학":
                return Str.JANGHACK_SSU_NOTICES;
            case "국제교류":
                return Str.KUCKJE_SSU_NOTICES;
            case "모집,채용":
                return Str.MOJIP_SSU_NOTICES;
            case "교내행사":
                return Str.KYONE_SSU_NOTICES;
            case "교외행사":
                return Str.KYOWAE_SSU_NOTICES;
            case "봉사":
                return Str.BONGSA_SSU_NOTICES;
            default:
                return Str.ALL_SSU_NOTICES;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
