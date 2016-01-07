package org.team10424102.whisky.models.extensions.dota2;

import org.team10424102.whisky.R;
import org.team10424102.whisky.models.enums.StringResourceProvided;

public enum EDota2MatchType implements StringResourceProvided {
    LADDER(R.string.enum_dota2_match_type_ladder);

    private int id;

    EDota2MatchType(int resId) {
        id = resId;
    }

    @Override
    public int getStringResId() {
        return id;
    }
}
