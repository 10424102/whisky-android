package org.team10424102.whisky.controllers;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableInt;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.team10424102.whisky.App;
import org.team10424102.whisky.R;
import org.team10424102.whisky.components.ApiCallback;
import org.team10424102.whisky.components.TokenResult;
import org.team10424102.whisky.databinding.ActivityVcodeBinding;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yy on 10/30/15.
 */
public class VcodeActivity extends BaseActivity {
    public static final int TYPE_REFRESH_TOKEN = 1;
    public static final int TYPE_REGISTER = 2;
    public static final int COUNTDOWN_LENGTH = 60;

    private String mPhone;
    private TextView mCountdown;
    private EditText mVcode;
    private Timer mTimer;
    private ObservableInt mCounter = new ObservableInt(COUNTDOWN_LENGTH);

    private void initToolbar(Toolbar toolbar) {
        // 初始化 Toolbar
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayShowTitleEnabled(true);
        ab.setDisplayHomeAsUpEnabled(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityVcodeBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_vcode);

        Intent intent = getIntent();
        int type = intent.getIntExtra("type", TYPE_REGISTER);
        mPhone = getIntent().getStringExtra("phone");

        mCountdown = binding.countdown;
        mVcode = binding.vcode;

        binding.setPhone(mPhone);
        binding.setRegister(type == TYPE_REGISTER);
        binding.setCounter(mCounter);

        initToolbar(binding.toolbar);


    }

    public void comfirm(View view) {
        mTimer.cancel();
        App.api().getToken(mPhone, mVcode.getText().toString())
                .enqueue(new ApiCallback<TokenResult>() {
                    @Override
                    protected void handleSuccess(TokenResult result) {
                        String phone = mPhone;
                        String token = result.getToken();

                        App.getProfile().setPhone(phone);
                        App.getProfile().setToken(token);
                        App.getDataService().saveToken(phone, token);

                        Intent intent = new Intent(VcodeActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();

        final SpannableString resendText =
                new SpannableString(getResources().getString(R.string.vcode_resend));
        resendText.setSpan(new UnderlineSpan(), 0, resendText.length(), 0);


        if (mTimer != null) {
            mTimer.cancel();
        }
        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (mCounter.get() > 0) {
                    mCounter.set(mCounter.get() - 1);
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mCountdown.setText(resendText);
                            mCountdown.setTextColor(Color.BLUE);
                        }
                    });
                }

            }
        }, 1000, 1000);
    }
}
