package com.notissu.UI.RssDialog.View;

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
import com.notissu.UI.RssDialog.Presenter.RssItemContract;
import com.notissu.UI.RssDialog.Presenter.RssItemPresenter;
import com.notissu.Util.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by forhack on 2016-11-09.
 */

public class RssItemDialog extends DialogFragment implements RssItemContract.View{
    public static final String TAG = LogUtils.makeLogTag(RssItemDialog.class);
    public static final String KEY_RSSITEM = "KEY_RSSITEM";
    @BindView(R.id.rssitem_tv_title)
    TextView mTvTitle;
    @BindView(R.id.rssitem_tv_time)
    TextView mTvTime;
    @BindView(R.id.rssitem_btn_visit_site)
    Button mBtnVisit;

    private RssItemContract.Presenter presenter;

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
        View view = inflater.inflate(R.layout.dialog_rssitem, null, false);
        ButterKnife.bind(this, view);

        presenter = new RssItemPresenter(getArguments(), this);
        presenter.start();

        mBtnVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addBrowser();
            }
        });

        return view;
    }

    @Override
    public void setPresenter(RssItemContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showTitle(String title) {
        mTvTitle.setText(title);
    }

    @Override
    public void showTime(String publishDateLong) {
        mTvTime.setText(publishDateLong);
    }

    @Override
    public void showBrowser(String link) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(link));
        startActivity(intent);
        dismiss();
    }
}
