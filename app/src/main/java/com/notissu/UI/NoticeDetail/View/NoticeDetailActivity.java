package com.notissu.UI.NoticeDetail.View;

import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.notissu.Model.Notice;
import com.notissu.Model.NoticeDetail;
import com.notissu.R;
import com.notissu.UI.NoticeDetail.Adapter.AttachedFileAdapter;
import com.notissu.UI.NoticeDetail.Adapter.AttachedFileAdapterContract;
import com.notissu.UI.NoticeDetail.Presenter.NoticeDetailContract;
import com.notissu.UI.NoticeDetail.Presenter.NoticeDetailPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoticeDetailActivity extends AppCompatActivity implements NoticeDetailContract.View {
    @BindView(R.id.notice_detail_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.notice_detail_title)
    TextView mTitle;
    @BindView(R.id.notice_detail_date)
    TextView mDate;
    @BindView(R.id.notice_detail_ll_attched_files)
    LinearLayout mAttchedFilesLayout;
    @BindView(R.id.notice_detail_rv_attached_files)
    RecyclerView mAttachedFilesList;
    @BindView(R.id.notice_detail_web_view)
    WebView mWebView;

    private NoticeDetailContract.Presenter mPresenter;
    private DownloadManager mDownloadManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDownloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

        mPresenter = new NoticeDetailPresenter(getIntent().getExtras(), this);
        mPresenter.fetchNoticeDetail();

        AttachedFileAdapter attachedFileAdapter = new AttachedFileAdapter();
        attachedFileAdapter.setOnRecyclerItemClickListener(new AttachedFileAdapterContract.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View itemView, RecyclerView.Adapter adapter, int position) {
                // FIXME: 2017-01-19 다운로드 관련 클래스로 따로 빼서 관리할 부분인 듯하다. 리팩토링 필요
                try {
                    mPresenter.onAttachedFileClick(position);
                } catch (Exception e) {
                    if (ContextCompat.checkSelfPermission(NoticeDetailActivity.this,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(NoticeDetailActivity.this,
                                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            ActivityCompat.requestPermissions(NoticeDetailActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    110);
                        } else {
                            ActivityCompat.requestPermissions(NoticeDetailActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    110);
                        }
                    }
                }
            }
        });
        mPresenter.setAttchedFileAdapter(attachedFileAdapter);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void showNoticeDetail(NoticeDetail noticeDetail) {
        mTitle.setText(noticeDetail.getTitle());
        mDate.setText(noticeDetail.getDate());
        mWebView.loadData(noticeDetail.getContents(), "text/html; charset=UTF-8", null);
        mWebView.setWebViewClient(new WebViewClient());
    }

    @Override
    public void setViews(Notice notice) {
        mTitle.setText(notice.getTitle());
        mDate.setText(notice.getShortDate());
    }

    @Override
    public void showAttchedFiles(AttachedFileAdapter attachedFileList) {
        mAttachedFilesList.setLayoutManager(new GridLayoutManager(this,2));
        mAttachedFilesList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAttachedFilesList.setAdapter(attachedFileList);
    }

    @Override
    public void hideAttchedFiles() {
        mAttchedFilesLayout.setVisibility(View.GONE);
    }

    @Override
    public void showDownload(DownloadManager.Request request) {
        mDownloadManager.enqueue(request);
    }
}
