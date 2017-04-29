package com.notissu.UI.NoticeList.Detail;

import android.app.DownloadManager;

import com.kakao.kakaolink.v2.model.FeedTemplate;
import com.notissu.Model.Notice;
import com.notissu.Model.NoticeDetail;

public interface NoticeDetailContract {
    interface View {
        void showNoticeDetail(NoticeDetail noticeDetail);

        void setViews(Notice notice);

        void setAttchedFiles(AttachedFileAdapter attachedFileList);

        void showDownload(DownloadManager.Request request);

        void showProgress();

        void hideProgress();

        void showFolder();

        void hideFolder();

        void showShareNotice(FeedTemplate params);
    }

    interface Presenter {
        void fetchNoticeDetail();

        void setAttchedFileAdapter(AttachedFileAdapter attachedFileAdapter);

        void onAttachedFileClick(int position);

        void setShareNotice();
    }

    interface OnFetchNoticeDetailListener {

        void onFetchNoticeDetail(String response);
    }
}
