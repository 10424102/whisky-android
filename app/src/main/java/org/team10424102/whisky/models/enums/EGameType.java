package org.team10424102.whisky.models.enums;

import org.team10424102.whisky.R;

public enum EGameType implements AndroidStringResourceProvided, AndroidDrawableResourceProvided {
    DOTA2(R.string.enum_game_type_dota2, R.drawable.game_dota2),
    LOL(R.string.enum_game_type_lol, R.drawable.game_lol),
    MINECRAFT(R.string.enum_game_type_minecraft, R.drawable.game_minecraft),
    CS(R.string.enum_game_type_cs, R.drawable.game_cs),
    CF(R.string.enum_game_type_cf, R.drawable.game_cf),
    STARCRAFT(R.string.enum_game_type_starcraft, R.drawable.game_starcraft),
    WARCRAFT(R.string.enum_game_type_warcraft, R.drawable.game_warcraft),
    HEARTSTONE(R.string.enum_game_type_heartstone, R.drawable.game_heartstone),
    WOW(R.string.enum_game_type_wow, R.drawable.game_wow);

    private int id;

    private int drawableId;

    EGameType(int resId, int drawableId) {
        id = resId;
        this.drawableId = drawableId;
    }

    @Override
    public int getStringResId() {
        return id;
    }


    @Override
    public int getDrawableResId() {
        return drawableId;
    }
}
