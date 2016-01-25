package org.team10424102.whisky.models;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.team10424102.whisky.App;
import org.team10424102.whisky.components.ImageRepo;

public class ImageViewWrapper implements Target {

    private ImageView mImageView;
    private ImageRepo mImageRepo;
    private String mHash;

    public ImageViewWrapper(ImageView imageView, String hash) {
        mImageView = imageView;
        mImageRepo = App.getInstance().getObjectGraph().get(ImageRepo.class);
    }

    @Override
    public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
        mImageView.setImageBitmap(bitmap);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mImageRepo.save(mHash, bitmap);
            }
        }).start();
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {

    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }
}
