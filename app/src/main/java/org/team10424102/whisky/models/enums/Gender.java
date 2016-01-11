package org.team10424102.whisky.models.enums;

import org.team10424102.whisky.R;

@Deprecated
public enum Gender implements StringResourceProvided {
    MALE(R.string.gender_male),
    FEMALE(R.string.gender_female),
    OTHER(R.string.gender_other),
    UNKNOWN(R.string.gender_unknown);

    private int id;

    Gender(int resId) {
        id = resId;
    }

    @Override
    public int getStringResId() {
        return id;
    }
}
