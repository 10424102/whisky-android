package org.team10424102.whisky.components.auth;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.team10424102.whisky.App;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.inject.Inject;

import timber.log.Timber;

public class AccountService extends Service {

    private final IBinder mBinder = new InnerBinder();
    private volatile Account mCurrentAccount;
    @Inject AccountRepo mAccountRepo;

    @Override
    public void onCreate() {
        App.getInstance().getObjectGraph().inject(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class InnerBinder extends Binder {
        public AccountService getService() {
            return AccountService.this;
        }
    }

    @Nullable
    public Account getCurrentAccount() {
        return mCurrentAccount;
    }

    public void setCurrentAccount(Account account) {
        mCurrentAccount = account;
    }

    /**
     * get all valid and visiable accounts
     * @return
     */
    public List<Account> getAllAccounts() {
        assert mAccountRepo != null: "缺少 AccountRepo 组件";
        return mAccountRepo.all();
    }

    public Account findByIdentity(AccountIdentity identity) {
        return null;
    }
}
