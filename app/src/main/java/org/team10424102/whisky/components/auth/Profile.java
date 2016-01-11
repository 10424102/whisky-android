package org.team10424102.whisky.components.auth;

import android.os.Parcelable;

import java.util.Properties;

public interface Profile extends Parcelable {

    Object getProperty(String name);

    void setProperty(String name, Object value);

    String[] getAllPropertyNames();

    Properties getAllProperties();
}
