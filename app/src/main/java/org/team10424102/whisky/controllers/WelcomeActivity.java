package org.team10424102.whisky.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.team10424102.whisky.App;
import org.team10424102.whisky.R;
import org.team10424102.whisky.components.ApiCallback;
import org.team10424102.whisky.components.AvailabilityResult;

/**
 * Created by yy on 10/30/15.
 */
public class WelcomeActivity extends Activity {
    private static final String TAG = "WelcomeActivity";

    private EditText mPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 低于 Jellybean 版本的 Android 使用下面的方法隐藏顶部状态栏
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        setContentView(R.layout.activity_welcome);
        mPhone = (EditText) findViewById(R.id.phone);

        mPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhone.setCursorVisible(true);
            }
        });
        mPhone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                mPhone.setCursorVisible(false);
                if (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager in =
                            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(mPhone.getApplicationWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return false;
            }
        });
    }

    public void commit(View view) {
        final String phone = mPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            mPhone.setError(getString(R.string.phone_empty));
            return;
        }

        App.getProfile().setPhone(phone);

        final String token = App.getDataService().getToken(phone);

        if (token != null) {

            App.getProfile().setToken(token);

            App.api().isTokenAvailable(token).enqueue(new ApiCallback<AvailabilityResult>() {
                @Override
                protected void handleSuccess(AvailabilityResult result) {
                    if (result.isAvailable()) {
                        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                        startActivity(intent);
                        return;
                    }

                    Intent intent = new Intent(WelcomeActivity.this, VcodeActivity.class);
                    intent.putExtra("phone", phone);
                    intent.putExtra("type", VcodeActivity.TYPE_REFRESH_TOKEN);
                    startActivity(intent);
                }
            });

            return;
        }


        Intent intent = new Intent(WelcomeActivity.this, VcodeActivity.class);
        intent.putExtra("phone", phone);
        intent.putExtra("type", VcodeActivity.TYPE_REGISTER);
        startActivity(intent);

    }
}
