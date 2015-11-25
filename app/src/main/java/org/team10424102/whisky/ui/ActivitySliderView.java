package org.team10424102.whisky.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;

import org.team10424102.whisky.R;
import org.team10424102.whisky.models.enums.EGameType;

public class ActivitySliderView extends BaseSliderView {

    private Drawable gameLogo;

    public ActivitySliderView(Context context) {
        super(context);
    }

    @SuppressWarnings("deprecation")
    public void setGame(EGameType type) {
        if (type == null) return;
        //gameLogo = getContext().getResources().getDrawable(type.getDrawableResId(), getContext().getTheme());
        gameLogo = getContext().getResources().getDrawable(type.getDrawableResId());
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.item_activity_slider, null);
        ImageView cover = (ImageView) v.findViewById(R.id.cover);
        TextView title = (TextView) v.findViewById(R.id.title);
        title.setText(getDescription());
        bindEventAndShow(v, cover);
        if (gameLogo != null) {
            ImageView logo = (ImageView) v.findViewById(R.id.game_logo);
            logo.setImageDrawable(gameLogo);
        }
        return v;
    }
}
