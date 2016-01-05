package org.team10424102.whisky.components.auth;

import android.content.Context;
import android.os.Parcelable;

import com.squareup.okhttp.Request;

public interface Authentication extends Parcelable {
    boolean isAuthenticated(Context context);
    Request authenticateHttpRequest(Request request);
}
