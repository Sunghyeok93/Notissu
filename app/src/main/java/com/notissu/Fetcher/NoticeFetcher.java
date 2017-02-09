package com.notissu.Fetcher;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.notissu.BaseApplication;
import com.notissu.UI.NoticeDetail.Presenter.NoticeDetailContract;
import com.notissu.UI.NoticeList.Presenter.NoticeListContract;
import com.notissu.UI.NoticeTab.Presenter.NoticeTabContract;

/**
 * Created by forhack on 2017-02-06.
 */

public class NoticeFetcher {
    private RequestQueue mQueue;
    private NoticeListContract.OnFetchNoticeListListener mOnFetchNoticeListListener;
    private NoticeDetailContract.OnFetchNoticeDetailListener mOnFetchNoticeDetailListener;

    public NoticeFetcher(NoticeListContract.OnFetchNoticeListListener onFetchNoticeListListener) {
        mQueue = BaseApplication.getRequestQueue();
        mOnFetchNoticeListListener = onFetchNoticeListListener;
    }

    public NoticeFetcher(NoticeDetailContract.OnFetchNoticeDetailListener mOnFetchNoticeDetailListener) {
        mQueue = BaseApplication.getRequestQueue();
        this.mOnFetchNoticeDetailListener = mOnFetchNoticeDetailListener;
    }

    public void fetchNoticeList(String categoryKo, int pageNum) {
        String categoryEng = changeCategoryToEng(categoryKo);
        String url = BaseApplication.BASE_URL + "list/"+categoryEng+"/" + pageNum;
        StringRequest request = new StringRequestUTF8(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mOnFetchNoticeListListener.onFetchNoticeList(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.print("");
            }
        });
        mQueue.add(request);
    }

    private String changeCategoryToEng(String category) {
        String serverCategory;
        switch (category) {
            case NoticeTabContract.NOTICE_SSU_ALL:
                serverCategory = "all";
                break;
            case NoticeTabContract.NOTICE_SSU_STUDENT:
                serverCategory = "student";
                break;
            case NoticeTabContract.NOTICE_SSU_SCHOLARSHIP:
                serverCategory = "scholarship";
                break;
            case NoticeTabContract.NOTICE_SSU_VOLUNTEER:
                serverCategory = "volunteer";
                break;
            case NoticeTabContract.NOTICE_SSU_FOREIGN:
                serverCategory = "foreign";
                break;
            case NoticeTabContract.NOTICE_SSU_GLOBAL:
                serverCategory = "global";
                break;
            case NoticeTabContract.NOTICE_SSU_RECRUIT:
                serverCategory = "recruit";
                break;
            case NoticeTabContract.NOTICE_SSU_INNER:
                serverCategory = "inner";
                break;
            case NoticeTabContract.NOTICE_SSU_OUTER:
                serverCategory = "outer";
                break;
            default:
                serverCategory = "all";
                break;
        }

        return serverCategory;
    }

    public void fetchNoticeDetail(int noticeId) {
        String url = BaseApplication.BASE_URL + "view/" + noticeId;
        StringRequest request = new StringRequestUTF8(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mOnFetchNoticeDetailListener.onFetchNoticeDetail(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        mQueue.add(request);
    }
}
