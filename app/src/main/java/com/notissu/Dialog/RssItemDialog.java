package com.notissu.Dialog;

import android.content.Intent;
import android.net.Uri;
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
    View mRootView;
    TextView mTvTitle;
    TextView mTvTime;
    Button mBtnVisit;

    RssItem mRssitem;

    public static RssItemDialog newInstance(RssItem rssitem) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_RSSITEM, rssitem);
        RssItemDialog fragment = new RssItemDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.dialog_rssitem, null, false);


        /*위젯을 초기화하는 함수*/
        initWidget();
        /*초기화한 위젯에 데이터를 처리하는 함수*/
        settingWidget();
        /*위젯에 리스터를 붙여주는 함수*/
        settingListener();

        return mRootView;
    }

    private void initWidget() {
        mBtnVisit = (Button) mRootView.findViewById(R.id.rssitem_btn_visit_site);
        mTvTitle = (TextView) mRootView.findViewById(R.id.rssitem_tv_title);
        mTvTime = (TextView) mRootView.findViewById(R.id.rssitem_tv_time);
    }

    private void settingWidget() {
        mRssitem = getArguments().getParcelable(KEY_RSSITEM);
        mTvTitle.setText(mRssitem.getTitle());
        mTvTime.setText(mRssitem.getPublishDateLong());
    }

    private void settingListener() {
        mBtnVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(mRssitem.getLink()));
                startActivity(intent);
                dismiss();
            }
        });
    }
}
