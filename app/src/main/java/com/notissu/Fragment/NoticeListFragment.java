package com.notissu.Fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.notissu.Adapter.NoticeAdapter;
import com.notissu.Dialog.RssItemDialog;
import com.notissu.Model.NoticeRow;
import com.notissu.Model.RssItem;
import com.notissu.R;

import java.util.ArrayList;

public class NoticeListFragment extends Fragment {
    private static final String KEY_NOTICE_ROWS = "KEY_NOTICE_ROWS";

    ListView noticeList;
    NoticeAdapter noticeAdapter;
    View rootView;
    ArrayList<NoticeRow> noticeRows;

    public static Fragment newInstance(ArrayList<NoticeRow> noticeRows) {
        Bundle bundle = new Bundle();
        //이름 제대로 지정해야한다..
        bundle.putParcelableArrayList(KEY_NOTICE_ROWS,noticeRows);
        Fragment fragment = new NoticeListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_notice_list, container, false);
        // Inflate the layout for this fragment

        initWidget();
        settingWidget();
        settingListener();

        return rootView;
    }

    private void initWidget() {
        noticeList = (ListView) rootView.findViewById(R.id.notice_list);
        noticeAdapter = new NoticeAdapter(getContext());
    }

    private void settingWidget() {
        Bundle bundle = getArguments();
        this.noticeRows = bundle.getParcelableArrayList(KEY_NOTICE_ROWS);
        noticeList.setAdapter(noticeAdapter);
        for (int i=0;i<noticeRows.size();i++) {
            noticeAdapter.add(noticeRows.get(i));
        }
    }

    private void settingListener() {
        noticeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FragmentManager manager = getFragmentManager();
                RssItem rssItem = new RssItem(
                        "[모집·채용]졸업 선배 멘토링 안내[현대상선/농협정보시스템]",
                        "","","2016.11.09 09:24:01");
                DialogFragment mydialog = RssItemDialog.newInstance(rssItem);
                mydialog.show(manager,"");
            }

        });
    }

}
