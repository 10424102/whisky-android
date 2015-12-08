package org.team10424102.whisky.ui;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.johnkil.print.PrintDrawable;

import org.team10424102.whisky.utils.DimensionUtils;

public class RightArrowBar extends FrameLayout {
    private static final float DEFAULT_HEIGHT = 48; //dp
    private static PrintDrawable mPrintDrawable;
    private LinearLayout mContentRoot;

    public RightArrowBar(Context context, AttributeSet attrs) {
        super(context, attrs);


        mContentRoot = new LinearLayout(getContext());

        setMinimumHeight(DimensionUtils.dp2px(DEFAULT_HEIGHT));
    }

    protected ViewGroup getContentRoot() {
        return mContentRoot;
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
        addRightArrow();
    }

    private void addRightArrow() {
        if (mPrintDrawable == null) {
            mPrintDrawable = new PrintDrawable.Builder(getContext())
                    .iconCode(0xf3d1)
                    .iconColor(Color.argb(100, 170, 170, 170))
                    .iconSize(TypedValue.COMPLEX_UNIT_DIP, 30)
                    .build();
        }

        FrameLayout.LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_VERTICAL | Gravity.RIGHT);

        ImageView image = new ImageView(getContext());
        image.setImageDrawable(mPrintDrawable);

        addView(image, lp);
    }

    @Override
    protected void onAttachedToWindow() {


        while (getChildCount() > 0) {
            View v = getChildAt(0);
            removeViewAt(0);
            mContentRoot.addView(v);
        }


        FrameLayout.LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_VERTICAL);
        lp.setMargins(0, 0, DimensionUtils.dp2px(60), 0);

        addView(mContentRoot, lp);


        super.onAttachedToWindow();


    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {


        super.onLayout(changed, left, top, right, bottom);
    }
}
