package org.team10424102.whisky.utils;

import org.team10424102.whisky.Global;
import org.team10424102.whisky.R;
import org.team10424102.whisky.models.Activity;
import org.team10424102.whisky.models.Profile;

/**
 * Created by yy on 11/14/15.
 */
public class EnumUtils {
    public static String gender(Profile.Gender gender) {
        switch (gender) {
            case MALE:
                return Global.context.getString(R.string.gender_male);
            case FEMALE:
                return Global.context.getString(R.string.gender_female);
            case SECRET:
                return Global.context.getString(R.string.gender_secret);
        }
        return null;
    }

    public static String activityType(Activity.Type value) {
        switch (value) {
            case SCHOOL:
                return Global.context.getString(R.string.type_school);
            case FRIENDS:
                return Global.context.getString(R.string.type_friends);
            case FOLLOWINGS:
                return Global.context.getString(R.string.type_followings);
            case RECOMMENDATIONS:
                return Global.context.getString(R.string.type_recommendations);
        }
        return null;
    }
}
