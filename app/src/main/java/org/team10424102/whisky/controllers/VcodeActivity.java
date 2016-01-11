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

import org.team10424102.whisky.R;
import org.team10424102.whisky.components.BlackServerApi;
import org.team10424102.whisky.components.TokenResult;
import org.team10424102.whisky.components.auth.Account;
import org.team10424102.whisky.components.auth.BlackServerAccount;
import org.team10424102.whisky.components.auth.PhoneTokenAuthentication;
import org.team10424102.whisky.databinding.VcodeActivityBinding;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by yy on 10/30/15.
 */
public class VcodeActivity extends BaseActivity {
    public static final int TYPE_REFRESH_TOKEN = 1;
    public static final int TYPE_REGISTER = 2;
    public static final int COUNTDOWN_LENGTH = 60;
    public static final String EXTRA_ACCOUNT = "account";
    public static final String EXTRA_TYPE = "type";
    public static final String EXTRA_INTENT = "intent";
    public static CountDownLatch finishedLock;

    private BlackServerAccount mAccount;
    private TextView mCountdown;
    private EditText mVcode;
    private ObservableInt mCounter = new ObservableInt(COUNTDOWN_LENGTH);
    @Inject BlackServerApi mApi;
    private int mType;
    private Subscription mSubscription;
    private SpannableString mResendText;
    private Observable<Long> mTimerObservable;

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
        VcodeActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.vcode_activity);
        mCountdown = binding.countdown;
        mVcode = binding.vcode;

        Intent intent = getIntent();
        mType = intent.getIntExtra(EXTRA_TYPE, TYPE_REGISTER);
        mAccount = intent.getParcelableExtra(EXTRA_ACCOUNT);

        binding.setPhone(mAccount.getIdentity().get());
        binding.setRegister(mType == TYPE_REGISTER);
        binding.setCounter(mCounter);
        initToolbar(binding.toolbar);

        mResendText = new SpannableString(getResources().getString(R.string.vcode_resend));
        mResendText.setSpan(new UnderlineSpan(), 0, mResendText.length(), 0);

        mTimerObservable = Observable.interval(1, TimeUnit.SECONDS)
                .take(COUNTDOWN_LENGTH)
                .doOnNext(index -> {
                    if (index >= COUNTDOWN_LENGTH - 1) return;
                    mCounter.set((int) (COUNTDOWN_LENGTH - index - 1));
                })
                .last()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void comfirm(View view) {
        mSubscription.unsubscribe();

        String phone = mAccount.getIdentity().get();
        String vcode = mVcode.getText().toString();

        mApi.getToken(phone, vcode)
                .map(TokenResult::getToken)
                .doOnNext(mAccount::setToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(token -> {
                    // FIXME mAccount is not the same account in AccountService
                    mAccount.setToken(token);
                    finish();
                    VcodeActivity.finishedLock.countDown();
                });
    }

    @Override
    protected void onStart() {
        super.onStart();

        mSubscription = mTimerObservable.subscribe(index -> resend());
    }

    private void resend() {
        mCountdown.setText(mResendText);
        mCountdown.setTextColor(Color.BLUE);
        mCountdown.setOnClickListener(v -> {
            mCountdown.setTextColor(Color.BLACK);
            mCounter.set(COUNTDOWN_LENGTH);
            v.setOnClickListener(null);
            mSubscription = mTimerObservable.subscribe(index -> resend());
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSubscription.unsubscribe();
    }
}
