package org.team10424102.whisky.controllers;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.team10424102.whisky.Global;
import org.team10424102.whisky.R;
import org.team10424102.whisky.components.ApiCallback;
import org.team10424102.whisky.models.RefreshTokenResult;
import org.team10424102.whisky.models.RegisterResult;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yy on 10/30/15.
 */
public class VcodeActivity extends AppCompatActivity {
    public static final int TYPE_LOGIN = 1;
    public static final int TYPE_REGISTER = 2;
    public static final int COUNTDOWN_LENGTH = 60;
    private static final String TAG = "WelcomeActivity";
    private String mPhone;
    private TextView mHint;
    private TextView mCountdown;
    private Button mConfirm;
    private EditText mVcode;
    private Timer mTimer;
    private LinearLayout mRegisterLisenceBox;

    private int mCounter = COUNTDOWN_LENGTH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vcode);
        Intent intent = getIntent();
        int type = intent.getIntExtra("type", TYPE_REGISTER);
        mPhone = getIntent().getStringExtra("phone");
        mHint = (TextView) findViewById(R.id.vcode_hint);
        mCountdown = (TextView) findViewById(R.id.countdown);
        mConfirm = (Button) findViewById(R.id.confirm);
        mVcode = (EditText) findViewById(R.id.vcode);
        mRegisterLisenceBox = (LinearLayout) findViewById(R.id.register_lisence_box);

        mHint.setText(String.format(getResources().getString(R.string.vcode_hint), mPhone));

        if (type == TYPE_LOGIN) {
            mConfirm.setText(R.string.vcode_login);
            mRegisterLisenceBox.setVisibility(View.GONE);
            mConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mTimer.cancel();
                    login();
                }
            });
        } else {
            mConfirm.setText(R.string.vcode_register);
            mConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mTimer.cancel();
                    register();
                }
            });
        }

    }

    private void register() {
        Global.apiService.register("86", mPhone, mVcode.getText().toString())
                .enqueue(new ApiCallback<RegisterResult>(this, 200) {
                    @Override
                    public void handleSuccess(RegisterResult result) {
                        Global.currentUserPhone = mPhone;
                        Global.currentUserToken = result.getToken();
                        Global.dataManager.saveToken(Global.currentUserPhone, Global.currentUserToken);
                        goToMainActivity();
                    }
                });
    }

    private void login() {
        Global.apiService.refreshToken("86", mPhone, mVcode.getText().toString())
                .enqueue(new ApiCallback<RefreshTokenResult>(this, 200) {
                    @Override
                    public void handleSuccess(RefreshTokenResult result) {
                        Global.currentUserPhone = mPhone;
                        Global.currentUserToken = result.getToken();
                        Global.dataManager.saveToken(Global.currentUserPhone, Global.currentUserToken);
                        goToMainActivity();
                    }
                });
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Resources res = getResources();
        final String countdownFormat = res.getString(R.string.vcode_countdown);
        final SpannableString resendText =
                new SpannableString(res.getString(R.string.vcode_resend));
        resendText.setSpan(new UnderlineSpan(), 0, resendText.length(), 0);
        mCountdown.setText(String.format(countdownFormat, mCounter));
        if (mTimer != null) {
            mTimer.cancel();
        }
        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (mCounter > 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mCountdown.setText(String.format(countdownFormat, mCounter));
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mCountdown.setText(resendText);
                            mCountdown.setTextColor(Color.BLUE);
                        }
                    });
                }
                mCounter = mCounter - 1;
            }
        }, 1000, 1000);
    }
}
