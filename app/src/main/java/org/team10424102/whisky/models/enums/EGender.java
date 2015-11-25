package org.team10424102.whisky.models.enums;

import org.team10424102.whisky.R;

public enum EGender implements AndroidStringResourceProvided {
    MALE(R.string.enum_gender_male),
    FEMALE(R.string.enum_gender_female),
    SECRET(R.string.enum_gender_secret);

    private int id;

    EGender(int resId) {
        id = resId;
    }


    @Override
    public int getStringResId() {
        return id;
    }
}
