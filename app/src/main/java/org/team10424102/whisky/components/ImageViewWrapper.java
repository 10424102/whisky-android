package org.team10424102.whisky.components;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.team10424102.whisky.App;

public class ImageViewWrapper implements Target {

    private ImageView mImageView;
    private ImageRepo mImageRepo;
    private String mHash;

    public ImageViewWrapper(ImageView imageView, String hash) {
        mImageView = imageView;
        mImageRepo = App.getInstance().getObjectGraph().get(ImageRepo.class);
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        mImageRepo.save(mHash, bitmap);
        mImageView.setImageBitmap(bitmap);
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {

    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }
}
