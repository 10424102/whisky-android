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

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yy on 10/30/15.
 */
public class WelcomeActivity extends BaseActivity {
    private static final String TAG = "WelcomeActivity";

    @Bind(R.id.phone) EditText mPhone;

    private AccountService mAccountService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);
        ButterKnife.bind(this);

        // 低于 Jellybean 版本的 Android 使用下面的方法隐藏顶部状态栏
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
        new PhoneValidationTask(mAccountService).execute(phone);
    }

    private class PhoneValidationTask extends AsyncTask<String, Void, Void> {

        private final AccountService mAccountService;

        public PhoneValidationTask(AccountService accountService) {
            mAccountService = accountService;
        }

        @Override
        protected Void doInBackground(String... params) {
            String phone = params[0];
            List<Account> accounts = mAccountService.getAllAccounts();
            Account account = null;
            for (Account a : accounts) {
                if (a.getProfile().getPhone().equals(phone)) {
                    account = a;
                    break;
                }
            }
            Context context = WelcomeActivity.this;
            if (account == null) {
                account = new BlackServerAccount(phone);
            }
            while (!account.isValid(context)) {
                account.activate(context);
            }
            account.save(context);
            jumpMainActivity(account);
            return null;
        }

    }

    private void jumpMainActivity(Account validAccount) {
        mAccountService.setCurrentAccount(validAccount);
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
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
