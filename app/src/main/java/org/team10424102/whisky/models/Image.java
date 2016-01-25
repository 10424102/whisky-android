package org.team10424102.whisky.models;

import android.net.Uri;
import android.widget.ImageView;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = LazyImageDeserializer.class)
public interface Image {
    void loadInto(ImageView view);

    Uri uri();
}
