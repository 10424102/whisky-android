package org.team10424102.whisky.models.extensions.dota2;

import org.team10424102.whisky.R;
import org.team10424102.whisky.models.enums.StringResourceProvided;

public enum EMatchResult implements StringResourceProvided {
    WIN(R.string.enum_match_result_win),
    LOSE(R.string.enum_match_result_lose),
    DRAW(R.string.enum_match_result_draw);

    private int id;

    EMatchResult(int restId) {
        id = restId;
    }

    @Override
    public int getStringResId() {
        return id;
    }
}
