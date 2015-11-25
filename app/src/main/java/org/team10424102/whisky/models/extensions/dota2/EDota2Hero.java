package org.team10424102.whisky.models.extensions.dota2;

import org.team10424102.whisky.R;
import org.team10424102.whisky.models.enums.AndroidStringResourceProvided;

public enum EDota2Hero implements AndroidStringResourceProvided {
    ANTI_MAGE(R.string.enum_dota2_hero_anti_mage),
    AXE(R.string.enum_dota2_hero_axe),
    BANE(R.string.enum_dota2_hero_bane),
    BLOODSEEKER(R.string.enum_dota2_hero_bloodseeker),
    CRYSTAL_MAIDEN(R.string.enum_dota2_hero_crystal_maiden),
    DROW_RANGER(R.string.enum_dota2_hero_drow_ranger),
    EARTHSHAKER(R.string.enum_dota2_hero_earthshaker),
    JUGGERNAUT(R.string.enum_dota2_hero_juggernaut),
    MIRANA(R.string.enum_dota2_mirana),
    MORPHLING(R.string.enum_dota2_morphling),
    SHADOW_FIEND(R.string.enum_dota2_hero_shadow_fiend),
    PHANTOM_LANCER(R.string.enum_dota2_hero_phantom_lancer);

    private int id;

    EDota2Hero(int resId) {
        id = resId;
    }


    @Override
    public int getStringResId() {
        return id;
    }
}
