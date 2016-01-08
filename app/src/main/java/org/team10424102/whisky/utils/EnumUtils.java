package org.team10424102.whisky.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import org.team10424102.whisky.models.enums.StringResourceProvided;
import org.team10424102.whisky.models.enums.Gender;

import java.util.ArrayList;
import java.util.List;

public class EnumUtils extends org.apache.commons.lang3.EnumUtils {

    @NonNull
    public static <E extends Enum<E>> List<String> getStringList(Context context, Class<E> cls) {
        if (!StringResourceProvided.class.isAssignableFrom(cls)) return new ArrayList<>(0);
        List<E> enums = getEnumList(cls);
        List<String> result = new ArrayList<>(enums.size());
        for (Enum e : enums) {
            StringResourceProvided sp = (StringResourceProvided) e;
            result.add(context.getString(sp.getStringResId()));
        }
        return result;
    }

//    public static <E extends StringResourceProvided> String toString(E e) {
//        return StringUtils.getString(e.getStringResId());
//    }

    public static String toString(Gender e) {
        return StringUtils.getString(e.getStringResId());
    }
}
