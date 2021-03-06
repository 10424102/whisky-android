package org.team10424102.whisky.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.squareup.picasso.Picasso;

import org.team10424102.whisky.App;
import org.team10424102.whisky.R;
import org.team10424102.whisky.components.DataBindingAdapters;
import org.team10424102.whisky.models.Activity;

import javax.inject.Inject;

public class ActivitySliderView extends BaseSliderView {

    private String gameLogoUrl;

    @Inject Picasso picasso;

    public ActivitySliderView(Context context, final Activity activity) {
        super(context);

        App.getInstance().getObjectGraph().inject(this);

        description(activity.getTitle());
        image(activity.getCover().uri().toString());
        setScaleType(BaseSliderView.ScaleType.CenterCrop);
        setPicasso(picasso);

        if (activity.getGame() != null) {
            gameLogoUrl = activity.getGame().getLogo().uri().toString();
        }
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.item_activity_slider, null);
        ImageView cover = (ImageView) v.findViewById(R.id.cover);
        TextView title = (TextView) v.findViewById(R.id.title);
        title.setText(getDescription());
        bindEventAndShow(v, cover);
        if (gameLogoUrl != null) {
            ImageView logo = (ImageView) v.findViewById(R.id.game_logo);
            getPicasso().load(gameLogoUrl).into(logo);
        }
        return v;
    }
}
