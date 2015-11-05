package org.team10424102.whisky.components;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.squareup.okhttp.Request;

import org.team10424102.whisky.Global;
import org.team10424102.whisky.controllers.MainActivity;
import org.team10424102.whisky.controllers.VcodeActivity;

import java.io.IOException;

/**
 * Created by yy on 11/7/15.
 */
public class AuthenticationManager {
    private static final String TAG = "AuthenticationManager";

    public Request addAuthHeader(Request request) {
        Log.d(TAG, "为请求 " + request.urlString() + " 添加 token：" + Global.currentUserToken);
        return request.newBuilder().addHeader("X-Token", Global.currentUserToken).build();
    }

    public void authenticateCurrentUser(Context context, String phone) {
        try {
            String token;
            if (isPhoneRegistered(phone)) {
                token = Global.dataManager.getToken(phone);
                if (token == null) {
                    refreshToken(context, phone);
                } else {
                    if (isTokenStillValid(token)) {
                        goToMainActivity(context, phone, token);
                    } else {
                        refreshToken(context, phone);
                    }
                }
            } else {
                register(context, phone);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isPhoneRegistered(String phone) throws IOException {
        return !Global.apiService.isPhoneAvailable(phone).execute().body();
    }

    private boolean isTokenStillValid(String token) throws IOException {
        return Global.apiService.isTokenAvailable(token).execute().body();
    }

    private void goToMainActivity(Context context, String phone, String token) {
        Global.currentUserPhone = phone;
        Global.currentUserToken = token;
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public void refreshToken(Context context, String phone) {
        askMobSendVcode();
        Intent intent = new Intent(context, VcodeActivity.class);
        intent.putExtra("phone", phone);
        intent.putExtra("type", VcodeActivity.TYPE_LOGIN);
        context.startActivity(intent);
    }

    private void register(Context context, String phone) {
        askMobSendVcode();
        Intent intent = new Intent(context, VcodeActivity.class);
        intent.putExtra("phone", phone);
        intent.putExtra("type", VcodeActivity.TYPE_REGISTER);
        context.startActivity(intent);
    }

    private void askMobSendVcode() {
        // TODO 等待 Mob 的 BUG 回复
    }
}
