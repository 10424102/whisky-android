package org.team10424102.whisky.components;

import android.content.Context;
import android.support.annotation.NonNull;

import org.team10424102.whisky.App;
import org.team10424102.whisky.databinding.ExtImageBinding;
import org.team10424102.whisky.models.Profile;

public class BlackServerAccount implements Account {
    private Authentication mAuth;
    private boolean mValid;
    private boolean mVisiable;
    private final Profile mProfile = new Profile();
    private int usedCount;

    public Authentication getAuth() {
        return mAuth;
    }

    public void setAuth(Authentication auth) {
        mAuth = auth;
    }

    public void setVisiable(boolean visiable) {
        mVisiable = visiable;
    }

    public int getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(int usedCount) {
        this.usedCount = usedCount;
    }

    @Override
    public boolean validate() {
        if (mAuth == null) return false;
        try {
            mValid = mAuth.authenticate();
        }catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isValid() {
        return mValid;
    }

    @Override
    public boolean isVisiable() {
        return mVisiable;
    }

    @NonNull
    @Override
    public Profile getProfile() {
        return mProfile;
    }
}
