package com.notissu.UI.NoticeDetail.Presenter;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import com.notissu.Fetcher.NoticeFetcher;
import com.notissu.Model.AttachedFile;
import com.notissu.Model.NoticeDetail;
import com.notissu.UI.NoticeDetail.Adapter.AttachedFileAdapter;
import com.notissu.UI.NoticeDetail.Adapter.AttachedFileAdapterContract;
import com.notissu.UI.NoticeList.View.NoticeListFragment;

import java.util.List;

public class NoticeDetailPresenter implements NoticeDetailContract.Presenter,
        NoticeDetailContract.OnFetchNoticeDetailListener {
    private NoticeDetailContract.View mView;
    private AttachedFileAdapterContract.Model mAdapterModel;
    private AttachedFileAdapterContract.View mAdapterView;

    private Bundle mBundle;
    private NoticeDetail mNoticeDetail;

    private int noticeId;

    public NoticeDetailPresenter(Bundle bundle, NoticeDetailContract.View view) {
        this.mView = view;
        this.mBundle = bundle;
        noticeId = mBundle.getInt(NoticeListFragment.KEY_NOTICE_ID);
    }

    @Override
    public void fetchNoticeDetail() {
        NoticeFetcher fetcher = new NoticeFetcher(this);
        fetcher.fetchNoticeDetail(noticeId);
    }

    @Override
    public void setAttchedFileAdapter(AttachedFileAdapter attachedFileAdapter) {
        mAdapterModel = attachedFileAdapter;
        mAdapterView = attachedFileAdapter;
        mView.showAttchedFiles(attachedFileAdapter);
    }

    @Override
    public void onAttachedFileClick(int position) {
        AttachedFile attachedFile = mAdapterModel.getItem(position);
        Uri urlToDownload = Uri.parse(attachedFile.getUrl());
        List<String> pathSegments = urlToDownload.getPathSegments();
        // FIXME: 2017-01-19 다운로드 관련 클래스 생기면 빠질 부분
        DownloadManager.Request request = new DownloadManager.Request(urlToDownload);
        request.setTitle(attachedFile.getTitle());
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, pathSegments.get(pathSegments.size()-1));
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdirs();
        mView.showDownload(request);
    }

    @Override
    public void onFetchNoticeDetail(String response) {
        mNoticeDetail = NoticeDetail.fromJson(response);

        mAdapterModel.setAttachedFileList(mNoticeDetail.getAttachedFileList());
        mAdapterView.refresh();

        if (mAdapterModel.getCount() == 0) {
            mView.hideAttchedFiles();
        }

        mView.showNoticeDetail(mNoticeDetail);
    }
}
