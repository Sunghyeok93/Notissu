package com.notissu.Dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.notissu.Model.RssItem;
import com.notissu.R;

/**
 * Created by forhack on 2016-11-09.
 */

public class RssItemDialog extends DialogFragment{
    private static final String KEY_RSSITEM = "KEY_RSSITEM";
    TextView tvTitle;
    TextView tvAuthor;
    TextView tvTime;
    Button btnVisit;
    View rootView;

    public static RssItemDialog newInstance(RssItem rssItem) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_RSSITEM,rssItem);
        RssItemDialog fragment = new RssItemDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_rssitem, null, false);


        /*위젯을 초기화하는 함수*/
        initWidget();
        /*초기화한 위젯에 데이터를 처리하는 함수*/
        settingWidget();
        /*위젯에 리스터를 붙여주는 함수*/
        settingListener();

        return rootView;
    }

    private void initWidget() {
        btnVisit = (Button) rootView.findViewById(R.id.rssitem_btn_visit_site);
        tvTitle = (TextView) rootView.findViewById(R.id.rssitem_tv_title);
        tvAuthor = (TextView) rootView.findViewById(R.id.rssitem_tv_author);
        tvTime = (TextView) rootView.findViewById(R.id.rssitem_tv_time);
    }

    private void settingWidget() {
        RssItem rssItem = getArguments().getParcelable(KEY_RSSITEM);
        tvTitle.setText(rssItem.getTitle());
        tvAuthor.setText(rssItem.getAuthor());
        tvTime.setText(rssItem.getDate());
    }

    private void settingListener() {

    }


}
