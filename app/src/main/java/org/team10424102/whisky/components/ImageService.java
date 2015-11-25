package org.team10424102.whisky.components;

import android.widget.ImageView;

import org.team10424102.whisky.models.LazyImage;

public interface ImageService {
    /**
     * 将图片加载到 ImageView 当中, 可能通过网络, 也可能是通过本地存储器
     *
     * @param image
     * @param view
     * @return
     */
    public boolean loadImageInto(LazyImage image, ImageView view);
}
