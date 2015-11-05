package org.team10424102.whisky.controllers;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;
import org.team10424102.whisky.Global;
import org.team10424102.whisky.R;
import org.team10424102.whisky.components.ApiCallback;
import org.team10424102.whisky.components.ErrorManager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yy on 10/30/15.
 */
public class WelcomeActivity extends Activity {
    private static final String TAG = "WelcomeActivity";

    private EditText phone;
    private Button loginOrRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 低于 Jellybean 版本的 Android 使用下面的方法隐藏顶部状态栏
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        setContentView(R.layout.activity_welcome);
        phone = (EditText) findViewById(R.id.phone);
        loginOrRegister = (Button) findViewById(R.id.login_or_register);
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == phone.getId()) {
                    phone.setCursorVisible(true);
                }
            }
        });
        phone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                phone.setCursorVisible(false);
                if (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager in =
                            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(phone.getApplicationWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return false;
            }
        });
        loginOrRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneStr = phone.getText().toString();
                if (TextUtils.isEmpty(phoneStr)) {
                    phone.setError(getString(R.string.phone_empty));
                    return;
                }
                new AuthenticationTask().execute(phoneStr);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Global.apiService.getServerStatus().enqueue(new ApiCallback<Integer>(this, 200) {
            @Override
            public void handleSuccess(Integer result) {
                if (result == Global.SERVER_STATUS_MAINTENANCE) {
                    Global.errorManager.handleError(WelcomeActivity.this, ErrorManager.ERR_SERVER_MAINTENANCE);
                }
            }
        });
    }

    private void setButtonText(@StringRes int resId, int counter) {
        String text = getResources().getString(resId) + StringUtils.repeat('.', counter);
        loginOrRegister.setText(text);
    }

    private class AuthenticationTask extends AsyncTask<String, Void, Void> {

        private Timer mTimer;
        private int counter = 0;

        @Override
        protected Void doInBackground(String... params) {
            mTimer = new Timer();
            mTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    counter = (counter + 1) % 4;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setButtonText(R.string.login_or_register, counter);
                        }
                    });
                }
            }, 0, 1000);
            Global.authenticationManager.authenticateCurrentUser(WelcomeActivity.this, params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mTimer.cancel();
            setButtonText(R.string.login_or_register, 0);
        }
    }
}
