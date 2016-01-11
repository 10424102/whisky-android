package org.team10424102.whisky.components.auth;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public interface Account extends Parcelable{

    boolean isVisiable();

    @NonNull
    Profile getProfile();

    @NonNull
    Authentication getAuthentication();

    AccountIdentity getIdentity();

    /**
     * Normally this method will call mAuth.isAuthenticated() to check if this account is valid.
     * NOTICE: Do not invoke this method on main thread
     *
     * @return
     */
    boolean isValid();

    /**
     * If the account is valid, invoking this method will activate it again.
     * NOTICE: Do not invoke this method on main thread
     *
     * @param context
     */
    void activate(Context context);

    void save(Context context);
}
