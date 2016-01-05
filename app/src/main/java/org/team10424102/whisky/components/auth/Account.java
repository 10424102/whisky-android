package org.team10424102.whisky.components.auth;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.team10424102.whisky.models.Profile;

public interface Account extends Parcelable{
    boolean isValid(Context context);
    Profile getProfile();
    void setProfile(Profile profile);
    boolean isVisiable();
    void activate(Context context);
    @NonNull
    Authentication getAuthentication();
    void setAuthentication(Authentication authentication);
    void save(Context context);
}
