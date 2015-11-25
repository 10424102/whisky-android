package org.team10424102.whisky.ui;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.team10424102.whisky.R;

public class MarginDownDecoration extends RecyclerView.ItemDecoration {
    private int margin;

    public MarginDownDecoration(Context context) {
        margin = context.getResources().getDimensionPixelSize(R.dimen.item_margin);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = margin;
    }
}
