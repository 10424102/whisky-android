package org.team10424102.whisky.models.enums;

import org.team10424102.whisky.R;

public enum Gender implements StringResourceProvided {
    MALE(R.string.enum_gender_male),
    FEMALE(R.string.enum_gender_female),
    OTHER(R.string.enum_gender_other),
    UNKNOWN(R.string.enum_gender_unknown);

    private int id;

    Gender(int resId) {
        id = resId;
    }

    @Override
    public int getStringResId() {
        return id;
    }
}
