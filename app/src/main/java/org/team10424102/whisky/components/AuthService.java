package org.team10424102.whisky.components;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.team10424102.whisky.App;
import org.team10424102.whisky.controllers.MainActivity;
import org.team10424102.whisky.controllers.VcodeActivity;

import java.io.IOException;

/**
 * Created by yy on 11/7/15.
 */
public class AuthService {
    private static final String TAG = "AuthService";

    private DataService mDataService;

    public AuthService(DataService dataService) {
        mDataService = dataService;
    }


    public void authenticateCurrentUser(Context context, String phone, String token) {
        try {
            if (isPhoneRegistered(phone)) {
                token = mDataService.getToken(phone);
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
            Log.e(TAG, "Network problem", e);
        }
    }

    private boolean isPhoneRegistered(String phone) throws IOException {
        return !App.api().isPhoneAvailable(phone).execute().body().isAvailable();
    }

    private boolean isTokenStillValid(String token) throws IOException {
        return App.api().isTokenAvailable(token).execute().body().isAvailable();
    }

    private void goToMainActivity(Context context, String phone, String token) {
        App.getProfile().setPhone(phone);
        App.getProfile().setToken(token);
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public void refreshToken(Context context, String phone) {
        askMobSendVcode();
        Intent intent = new Intent(context, VcodeActivity.class);
        intent.putExtra("phone", phone);
        intent.putExtra("type", VcodeActivity.TYPE_REFRESH_TOKEN);
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
