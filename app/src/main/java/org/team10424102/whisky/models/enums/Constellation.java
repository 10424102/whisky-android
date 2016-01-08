package org.team10424102.whisky.models.enums;

import org.team10424102.whisky.R;

public enum Constellation implements StringResourceProvided {

    ARIES(R.string.enum_constellation_aries),
    TAURUS(R.string.enum_constellation_taurus),
    GEMINI(R.string.enum_constellation_gemini),
    CANCER(R.string.enum_constellation_cancer),
    LEO(R.string.enum_constellation_leo),
    VIRGO(R.string.enum_constellation_virgo),
    LIBRA(R.string.enum_constellation_libra),
    SCORPIO(R.string.enum_constellation_scorpio),
    SAGITTARIUS(R.string.enum_constellation_sagittarius),
    CAPRICORN(R.string.enum_constellation_capricorn),
    AQUARIUS(R.string.enum_constellation_aquarius),
    PISCES(R.string.enum_constellation_pisces);

    private int id;

    Constellation(int resId) {
        id = resId;
    }


    @Override
    public int getStringResId() {
        return id;
    }
}
