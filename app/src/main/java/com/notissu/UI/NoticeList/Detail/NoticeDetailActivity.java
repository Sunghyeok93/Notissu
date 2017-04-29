package com.notissu.UI.NoticeList.Detail;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.notissu.Model.Notice;
import com.notissu.Model.NoticeDetail;
import com.notissu.Network.Util.StringRequestUTF8;
import com.notissu.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NoticeDetailActivity extends AppCompatActivity implements NoticeDetailContract.View {
    private static final String TAG = NoticeDetailActivity.class.getSimpleName();
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
    @BindView(R.id.notice_detail_btn_folder)
    ImageView mFolder;

    private NoticeDetailContract.Presenter mPresenter;
    private DownloadManager mDownloadManager;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("학교 서버가 너무 느려요...");

        mDownloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

        mPresenter = new NoticeDetailPresenter(getIntent().getExtras(), this);

        AttachedFileAdapter attachedFileAdapter = new AttachedFileAdapter();
        mPresenter.setAttchedFileAdapter(attachedFileAdapter);
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

        mPresenter.fetchNoticeDetail();
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick(R.id.notice_detail_btn_folder)
    void onFolderClick() {
        if (mAttchedFilesLayout.getVisibility() == View.VISIBLE) {
            hideAttchedFiles();
            mFolder.setBackgroundResource(R.drawable.ic_arrow_drop_down_black_24dp);
        } else if (mAttchedFilesLayout.getVisibility() == View.GONE) {
            showAttchedFiles();
            mFolder.setBackgroundResource(R.drawable.ic_arrow_drop_up_black_24dp);
        }
    }

    private void showAttchedFiles() {
        mAttchedFilesLayout.setVisibility(View.VISIBLE);
    }

    private void hideAttchedFiles() {
        mAttchedFilesLayout.setVisibility(View.GONE);
    }

    @Override
    public void showNoticeDetail(NoticeDetail noticeDetail) {
        mTitle.setText(noticeDetail.getTitle());
        mDate.setText(noticeDetail.getDate());
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setDisplayZoomControls(false);
        mWebView.loadData(noticeDetail.getContents(), "text/html; charset=UTF-8", null);
        mWebView.setWebViewClient(new WebViewClient());
    }

    @Override
    public void setViews(Notice notice) {
        mTitle.setText(notice.getTitle());
        mDate.setText(notice.getShortDate());
    }

    @Override
    public void setAttchedFiles(AttachedFileAdapter attachedFileList) {
        hideAttchedFiles();
        mAttachedFilesList.setLayoutManager(new GridLayoutManager(this, 2));
        mAttachedFilesList.setAdapter(attachedFileList);
    }

    @Override
    public void showDownload(DownloadManager.Request request) {
        mDownloadManager.enqueue(request);
    }

    @Override
    public void showProgress() {
        mProgressDialog.show();
    }

    @Override
    public void hideProgress() {
        mProgressDialog.dismiss();
    }

    @Override
    public void showFolder() {
        mFolder.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideFolder() {
        mFolder.setVisibility(View.INVISIBLE);
    }
}
