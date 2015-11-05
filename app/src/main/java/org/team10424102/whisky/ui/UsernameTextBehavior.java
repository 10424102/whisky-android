package org.team10424102.whisky.ui;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by yy on 11/6/15.
 */
@SuppressWarnings("unused")
public class UsernameTextBehavior extends CoordinatorLayout.Behavior<LinearLayout> {

    private float mFinalX;
    private float mStartX;


    public UsernameTextBehavior(Context context, AttributeSet attrs) {
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, LinearLayout child, View dependency) {
        return dependency instanceof ViewPager;

    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, LinearLayout child, View dependency) {
        if (mStartX == 0) {
            mStartX = child.getX();
            child.getWidth();
        }
        return false;
    }
}
