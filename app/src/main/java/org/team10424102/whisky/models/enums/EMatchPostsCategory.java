package org.team10424102.whisky.models.enums;

import org.team10424102.whisky.R;

public enum EMatchPostsCategory implements AndroidStringResourceProvided {
    SCHOOL(R.string.enum_match_posts_category_school),
    FRIENDS(R.string.enum_match_posts_category_friends),
    FOCUSES(R.string.enum_match_posts_category_focuses);

    private int id;

    EMatchPostsCategory(int resId) {
        id = resId;
    }


    @Override
    public int getStringResId() {
        return id;
    }
}
