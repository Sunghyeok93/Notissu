package com.notissu.Network;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.notissu.BaseApplication;
import com.notissu.Model.NavigationMenu;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by forhack on 2017-03-01.
 */

public class KeywordNetwork {
    public static final String TAG = KeywordNetwork.class.getSimpleName();
    private static final String BASE_URL = BaseApplication.BASE_URL + "keyword/";

    private RequestQueue mQueue = BaseApplication.getRequestQueue();
    private NavigationMenu.OnFetchKeywordListener mOnFetchKeywordListener;

    public KeywordNetwork() {
    }

    public KeywordNetwork(NavigationMenu.OnFetchKeywordListener onFetchKeywordListener) {
        this.mOnFetchKeywordListener = onFetchKeywordListener;
    }

    public void sendKeyword(final String title) {
        String token = FirebaseInstanceId.getInstance().getToken();
        String url = BASE_URL + token +"/";
        StringRequest request = new StringRequestUTF8(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("keyword", title);
                return params;
            }
        };
        mQueue.add(request);
    }

    public void fetchKeywordList() {
        String token = FirebaseInstanceId.getInstance().getToken();
        String url = BASE_URL + token +"/";
        StringRequest request = new StringRequestUTF8(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mOnFetchKeywordListener.onFetchKeyword(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(request);

    }
}
