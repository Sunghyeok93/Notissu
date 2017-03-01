package com.notissu.Network;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.notissu.BaseApplication;
import com.notissu.Firebase.MyFirebaseInstanceIDService;
import com.notissu.UI.Splach.SplashContract;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;

import java.util.HashMap;
import java.util.Map;

public class TokenSender {
    public static final String TAG = TokenSender.class.getSimpleName();
    private static final String BASE_URL = BaseApplication.BASE_URL + "token/";

    private RequestQueue mQueue = BaseApplication.getRequestQueue();
    private MyFirebaseInstanceIDService.OnSendTokenListener mOnSendTokenListener;

    public TokenSender(MyFirebaseInstanceIDService.OnSendTokenListener onSendTokenListener) {
        mOnSendTokenListener = onSendTokenListener;
    }

    public void tokenSend(final String token) {

        StringRequest request = new StringRequestUTF8(Request.Method.POST, BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonParser jsonParser = new JsonParser();
                JsonObject gsonObject = (JsonObject) jsonParser.parse(response);
                mOnSendTokenListener.onSendToken(gsonObject);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", token);
                return params;
            }
        };
        mQueue.add(request);
    }
}
