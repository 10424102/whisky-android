package org.team10424102.whisky.dev;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

public class UserListItem extends BaseObservable {

    private String phone;
    private String token;


    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Bindable
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
