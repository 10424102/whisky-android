package org.team10424102.whisky.controllers;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
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
import rx.functions.Action1;
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

        // 隐藏顶部状态来
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

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

    @OnClick(R.id.login_or_register)
    public void commit(View view) {
        final String phone = mPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            mPhone.setError(getString(R.string.phone_empty));
            return;
        }

        Observable.just(phone)
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String phone) {
                        AccountManager accountManager = AccountManager.get(WelcomeActivity.this);
                        Account[] accounts = accountManager.getAccountsByType("org.projw");
                        Account myAccount = null;
                        for (Account account : accounts) {
                            if (account.name.equals(phone)) {
                                myAccount = account;
                            }
                        }
                        if (myAccount == null) {
                            Intent intent = new Intent(WelcomeActivity.this, VcodeActivity.class);
                            startActivity(intent);
                        } else {
                            final AccountManagerFuture<Bundle> future = accountManager.getAuthToken(myAccount,
                                    "unknown",null, WelcomeActivity.this, null, null);
                            Bundle result;
                            try {
                                result = future.getResult();
                            } catch (Exception e) {
                                throw new AuthFailureException("无法获取用户 token");
                            }
                            String token = null;
                            if (future.isDone() && !future.isCancelled()) {
                                token = result.getString(AccountManager.KEY_AUTHTOKEN);
                            }
                            return token;
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String token) {
                        AccountManager accountManager = AccountManager.get(WelcomeActivity.this);
                        
                    }
                });





        // activate the account
//        Observable.just(phone)
//                .map(new Func1<String, BlackServerAccountIdentity>() {
//                    @Override
//                    public BlackServerAccountIdentity call(String phone) {
//                        return new BlackServerAccountIdentity(phone);
//                    }
//                })
//                .map(new Func1<BlackServerAccountIdentity, Account>() {
//                    @Override
//                    public Account call(BlackServerAccountIdentity identity) {
//                        return mAccountService.findByIdentity(identity);
//                    }
//                })
//                .map(new Func1<Account, Account>() {
//                    @Override
//                    public Account call(Account account) {
//                        if (account == null) {
//                            account = new BlackServerAccount(phone);
//                        }
//                        mAccountService.setCurrentAccount(account);
//                        Context context = WelcomeActivity.this;
//                        if (!account.isValid()) account.activate(context);
//                        account.save(context);
//                        return account;
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<Account>() {
//                    @Override
//                    public void call(Account account) {
//                        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
//                        startActivity(intent);
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        Timber.e(throwable, "failed to activate the account: phone = " + phone);
//                    }
//                });
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
