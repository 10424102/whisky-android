package org.team10424102.whisky.components;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.team10424102.whisky.models.Profile;

import java.util.List;

public class AccountService extends Service {

    private final IBinder mBinder = new AccountBinder();
    private Account mCurrentAccount;
    private AccountRepo mAccountRepo;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class AccountBinder extends Binder {
        AccountService getService() {
            return AccountService.this;
        }
    }

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
        List<Account> results = mAccountRepo.all();
        for (Account account: results) {
            account.validate();
            if (account.isValid() && account.isVisiable()) continue;
            results.remove(account);
        }
        return results;
    }
}
