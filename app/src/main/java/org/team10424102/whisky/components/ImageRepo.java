package org.team10424102.whisky.components;

import android.graphics.Bitmap;

public interface ImageRepo {

    Bitmap getImage(String hash);

    void save(String hash, Bitmap bitmap);
}
