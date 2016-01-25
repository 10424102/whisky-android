package org.team10424102.whisky.controllers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableInt;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
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
import org.team10424102.whisky.components.auth.AccountService;
import org.team10424102.whisky.components.auth.BlackServerAccount;
import org.team10424102.whisky.components.auth.PhoneTokenAuthentication;
import org.team10424102.whisky.databinding.VcodeActivityBinding;
import org.team10424102.whisky.models.BlackServer;

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
import rx.functions.Func1;
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

    private TextView mCountdown;
    private EditText mVcode;
    private ObservableInt mCounter = new ObservableInt(COUNTDOWN_LENGTH);
    @Inject BlackServerApi mApi;
    private int mType;
    private Subscription mSubscription;
    private SpannableString mResendText;
    private Observable<Long> mTimerObservable;
    private BlackServerAccount mAccount;
    private final CountDownLatch mAccountLock = new CountDownLatch(1);
    private VcodeActivityBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.vcode_activity);
        mCountdown = mBinding.countdown;
        mVcode = mBinding.vcode;

        // get arguments
        Intent intent = getIntent();
        mType = intent.getIntExtra(EXTRA_TYPE, TYPE_REGISTER);

        // bind account service
        intent = new Intent(this, AccountService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        // type: login/register
        mBinding.setRegister(mType == TYPE_REGISTER);

        // counter
        mBinding.setCounter(mCounter);

        // toolbar
        setSupportActionBar(mBinding.toolbar);
        assert getSupportActionBar() != null;
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayShowTitleEnabled(true);
        ab.setDisplayHomeAsUpEnabled(false);

        mResendText = new SpannableString(getResources().getString(R.string.vcode_resend));
        mResendText.setSpan(new UnderlineSpan(), 0, mResendText.length(), 0);

        mTimerObservable = Observable.interval(1, TimeUnit.SECONDS)
                .take(COUNTDOWN_LENGTH)
                .doOnNext(new Action1<Long>() {
                    @Override
                    public void call(Long index) {
                        if (index >= COUNTDOWN_LENGTH - 1) return;
                        mCounter.set((int) (COUNTDOWN_LENGTH - index - 1));
                    }
                })
                .last()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void comfirm(View view) {
        mSubscription.unsubscribe();

        // wait until account service binded
        try {
            mAccountLock.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String phone = mAccount.getIdentity().get();
        String vcode = mVcode.getText().toString();

        mApi.getToken(phone, vcode)
                .map(new Func1<TokenResult, String>() {
                    @Override
                    public String call(TokenResult tokenResult) {
                        return tokenResult.getToken();
                    }
                })
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(String token) {
                        mAccount.setToken(token);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String token) {
                        finish();
                        VcodeActivity.finishedLock.countDown();
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();

        mSubscription = mTimerObservable.subscribe(new Action1<Long>() {
            @Override
            public void call(Long index) {
                resend();
            }
        });
    }

    private void resend() {
        mCountdown.setText(mResendText);
        mCountdown.setTextColor(Color.BLUE);
        mCountdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCountdown.setTextColor(Color.BLACK);
                mCounter.set(COUNTDOWN_LENGTH);
                v.setOnClickListener(null);
                mSubscription = mTimerObservable.subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long index) {
                        resend();
                    }
                });
            }
        });
    }

    @Override
    protected void onStop() {
        mSubscription.unsubscribe();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            AccountService.InnerBinder binder = (AccountService.InnerBinder) service;
            AccountService accountService = binder.getService();

            mAccount = (BlackServerAccount) accountService.getCurrentAccount();
            if (mAccount == null) throw new IllegalStateException("account is null");

            mAccountLock.countDown();

            mBinding.setPhone(mAccount.getIdentity().get());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
