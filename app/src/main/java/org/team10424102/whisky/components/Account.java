package org.team10424102.whisky.components;

import android.support.annotation.NonNull;

import org.team10424102.whisky.models.Profile;

public interface Account {
    /**
     *
     * @return if the Account instance has been validated with the server regardless of results
     */
    boolean validate();

    /**
     *
     * @return if this account is valid of not
     */
    boolean isValid();

    /**
     *
     * @return if this account will be seen in WelcomeActivity phone EditText
     */
    boolean isVisiable();

    @NonNull
    Profile getProfile();
}
