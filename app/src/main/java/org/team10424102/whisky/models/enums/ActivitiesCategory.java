package org.team10424102.whisky.models.enums;

import org.team10424102.whisky.R;

public enum ActivitiesCategory implements AndroidStringResourceProvided {

    SCHOOL(R.string.enum_activities_category_school),
    FRIENDS(R.string.enum_activities_category_friends),
    FOCUSES(R.string.enum_activities_category_focuses),
    RECOMMENDATIONS(R.string.enum_activities_category_recommendations);

    private int id;

    ActivitiesCategory(int resId) {
        id = resId;
    }


    @Override
    public int getStringResId() {
        return id;
    }
}
