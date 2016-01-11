package org.team10424102.whisky.components.auth;

import android.os.Parcelable;

public interface AccountIdentity<T> extends Parcelable {
    T get();
}
