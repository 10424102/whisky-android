package org.team10424102.whisky.controllers;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableInt;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.team10424102.whisky.App;
import org.team10424102.whisky.R;
import org.team10424102.whisky.components.auth.Account;
import org.team10424102.whisky.components.ApiCallback;
import org.team10424102.whisky.components.api.ApiService;
import org.team10424102.whisky.components.auth.PhoneTokenAuthentication;
import org.team10424102.whisky.components.TokenResult;
import org.team10424102.whisky.databinding.ActivityVcodeBinding;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yy on 10/30/15.
 */
public class VcodeActivity extends AppCompatActivity {
    public static final String TAG = "VcodeActivity";
    public static final int TYPE_REFRESH_TOKEN = 1;
    public static final int TYPE_REGISTER = 2;
    public static final int COUNTDOWN_LENGTH = 60;
    public static final String EXTRA_ACCOUNT = "account";
    public static final String EXTRA_TYPE = "type";
    public static final String EXTRA_INTENT = "intent";

    private Account mAccount;
    private TextView mCountdown;
    private EditText mVcode;
    private Timer mTimer;
    private ObservableInt mCounter = new ObservableInt(COUNTDOWN_LENGTH);
    private ApiService mApiService;
    private Intent mIntent;
    private int mType;

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
        mCountdown = binding.countdown;
        mVcode = binding.vcode;

        Intent intent = getIntent();
        mType = intent.getIntExtra(EXTRA_TYPE, TYPE_REGISTER);
        mAccount = intent.getParcelableExtra(EXTRA_ACCOUNT);
        mIntent = intent.getParcelableExtra(EXTRA_INTENT);

        binding.setPhone(mAccount.getProfile().getPhone());
        binding.setRegister(mType == TYPE_REGISTER);
        binding.setCounter(mCounter);
        initToolbar(binding.toolbar);

        mApiService = (ApiService)((App)getApplication()).getComponent(ApiService.class);
    }

    public void comfirm(View view) {
        mTimer.cancel();

        final String phone = mAccount.getProfile().getPhone();
        String vcode = mVcode.getText().toString();

        mApiService.getToken(phone, vcode)
                .enqueue(new ApiCallback<TokenResult>() {
                    @Override
                    protected void handleSuccess(TokenResult result) {
                        String token = result.getToken();

                        PhoneTokenAuthentication auth = new PhoneTokenAuthentication(phone, token);
                        mAccount.setAuthentication(auth);

                        finish();

                        // TODO if this code will be executed
                        startActivity(mIntent);
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
