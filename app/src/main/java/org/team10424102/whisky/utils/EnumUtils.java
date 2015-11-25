package org.team10424102.whisky.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import org.team10424102.whisky.models.enums.AndroidStringResourceProvided;

import java.util.ArrayList;
import java.util.List;

public class EnumUtils extends org.apache.commons.lang3.EnumUtils {

    @NonNull
    public static <E extends Enum<E>> List<String> getStringList(Context context, Class<E> cls) {
        if (!AndroidStringResourceProvided.class.isAssignableFrom(cls)) return new ArrayList<>(0);
        List<E> enums = getEnumList(cls);
        List<String> result = new ArrayList<>(enums.size());
        for (Enum e : enums) {
            AndroidStringResourceProvided sp = (AndroidStringResourceProvided) e;
            result.add(context.getString(sp.getStringResId()));
        }
        return result;
    }
}
