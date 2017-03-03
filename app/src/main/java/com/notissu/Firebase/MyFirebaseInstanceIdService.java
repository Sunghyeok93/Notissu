package com.notissu.Firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.gson.JsonObject;
import com.notissu.Network.TokenSender;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    private static final String TAG = MyFirebaseInstanceIdService.class.getSimpleName();
    private static final String KEY_TOKEN_REGISTER = "KEY_TOKEN_REGISTER";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, token);
        TokenSender tokenSender = new TokenSender(onSendTokenListener);
        tokenSender.sendToken(token);
    }

    private OnSendTokenListener onSendTokenListener = new OnSendTokenListener() {
        @Override
        public void onSendToken(JsonObject response) {
            String result = response.get("result").getAsString();
            if (result.equals("ADD") || result.equals("UPDATE")) {
                Log.i(TAG, "Token Sended");
            } else if (result.equals("FAIL")) {
                Log.e(TAG, "Token send error ");
            }
        }
    };

    public interface OnSendTokenListener {
        void onSendToken(JsonObject response);
    }
}
