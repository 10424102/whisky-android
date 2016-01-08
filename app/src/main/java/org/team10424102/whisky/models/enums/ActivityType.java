package org.team10424102.whisky.models.enums;

import org.team10424102.whisky.R;

public enum ActivityType implements StringResourceProvided {
    CIVIL_WAR(R.string.enum_activity_type_civil_war),
    MATCH(R.string.enum_activity_type_match);

    private int id;

    ActivityType(int resId) {
        id = resId;
    }


    @Override
    public int getStringResId() {
        return id;
    }
}
