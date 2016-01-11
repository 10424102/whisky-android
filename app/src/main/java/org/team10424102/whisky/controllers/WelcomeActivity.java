package org.team10424102.whisky.controllers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.team10424102.whisky.R;
import org.team10424102.whisky.components.auth.Account;
import org.team10424102.whisky.components.auth.AccountService;
import org.team10424102.whisky.components.auth.BlackServerAccount;
import org.team10424102.whisky.components.auth.BlackServerAccountIdentity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by yy on 10/30/15.
 */
public class WelcomeActivity extends BaseActivity {

    @Bind(R.id.phone) EditText mPhone;

    private AccountService mAccountService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);
        ButterKnife.bind(this);

        // hide system top status bar
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        mPhone.setOnClickListener(v -> mPhone.setCursorVisible(true));

        mPhone.setOnEditorActionListener((TextView v, int actionId, KeyEvent event) -> {
            mPhone.setCursorVisible(false);
            if (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                InputMethodManager in =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(mPhone.getApplicationWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
            return false;
        });
    }

    @OnClick(R.id.login_or_register)
    public void commit(View view) {
        final String phone = mPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            mPhone.setError(getString(R.string.phone_empty));
            return;
        }
        //new PhoneValidationTask(mAccountService).execute(phone);

        // activate the account
        Subscription activation = Observable.just(phone)
                .map(BlackServerAccountIdentity::new)
                .map(mAccountService::findByIdentity)
                .map(account -> {
                    if (account == null) return new BlackServerAccount(phone);
                    return account;
                })
                .doOnNext(account -> {
                    if (!account.isValid()) account.activate(this);
                })
                .doOnNext(account -> account.save(this))
                .doOnNext(mAccountService::setCurrentAccount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(account -> {
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                }, throwable -> {
                    Timber.e(throwable, "failed to activate the account: phone = " + phone);
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // bind to AccountService
        Intent intent = new Intent(this, AccountService.class);
        bindService(intent, mConnention, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(mConnention);
    }

    private ServiceConnection mConnention = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            AccountService.InnerBinder binder = (AccountService.InnerBinder) service;
            mAccountService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };
}
