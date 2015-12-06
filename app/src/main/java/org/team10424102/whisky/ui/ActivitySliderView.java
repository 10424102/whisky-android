package org.team10424102.whisky.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;

import org.team10424102.whisky.App;
import org.team10424102.whisky.R;
import org.team10424102.whisky.models.Activity;

public class ActivitySliderView extends BaseSliderView {

    private String gameLogoUrl;

    public ActivitySliderView(Context context, final Activity activity) {
        super(context);

        description(activity.getTitle());

        image(App.getLazyImageUrl(activity.getCover()));

        setScaleType(BaseSliderView.ScaleType.CenterCrop);

        setOnSliderClickListener(new OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView slider) {
                Toast.makeText(getContext(), "Activity: " + activity.getId(), Toast.LENGTH_LONG).show();
            }
        });

        setPicasso(App.getPicasso());

        bundle(new Bundle());
        getBundle().putLong("id", activity.getId());


        if (activity.getGame() != null) {
            gameLogoUrl = App.getLazyImageUrl(activity.getGame().getLogo());
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
