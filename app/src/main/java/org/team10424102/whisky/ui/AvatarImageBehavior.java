package org.team10424102.whisky.ui;

import android.content.Context;
import android.content.res.Resources;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import org.team10424102.whisky.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yy on 11/6/15.
 */
@SuppressWarnings("unused")
public class AvatarImageBehavior extends CoordinatorLayout.Behavior<CircleImageView> {
    private float mAvatarUsernameGap;
    private float mStartViewPagerPosition;
    private float mAppHeaderHeight;
    private float mLength;
    private float mStartX;
    private float mFinalX;
    private float mStartY;
    private float mFinalY;
    private float mStartSize;
    private float mFinalSize;
    private float mScreenWidth;
    private float mXLength;
    private float mYLength;
    private float mSizeLength;
    private int mCounter;

    public AvatarImageBehavior(Context context, AttributeSet attrs) {
        final Resources res = context.getResources();
        mStartSize = res.getDimensionPixelSize(R.dimen.profile_avatar_size);
        mFinalSize = res.getDimensionPixelSize(R.dimen.profile_avatar_size_small);
        mAvatarUsernameGap = res.getDimensionPixelSize(R.dimen.profile_avatar_username_gap);

        mSizeLength = mFinalSize - mStartSize;
        float statusBarHeight = getStatusBarHeight(context);
        float headerHeight = res.getDimensionPixelSize(R.dimen.profile_header_height_min);
        mAppHeaderHeight = headerHeight + statusBarHeight;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        mScreenWidth = metrics.widthPixels;

        float defaultToolbarHeight = res.getDimensionPixelSize(R.dimen.profile_toolbar_height);
        float h = defaultToolbarHeight / 2 + statusBarHeight;
        mFinalY = h - mFinalSize / 2;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, CircleImageView child, View dependency) {
        return dependency instanceof ViewPager;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, CircleImageView child, View dependency) {
        float y = dependency.getY();
        if (mCounter <= 1) {
            if (mCounter == 0) {
                mCounter = 1;
                return false;
            }
            mStartViewPagerPosition = y;
            mLength = mStartViewPagerPosition - mAppHeaderHeight;
            mStartX = child.getX();
            mStartY = child.getY();
            LinearLayout username = (LinearLayout) parent.findViewById(R.id.username);
            //float w = mFinalSize + mAvatarUsernameGap + username.getWidth();
            float w = mFinalSize;
            mFinalX = (mScreenWidth - w) / 2;
            mXLength = mFinalX - mStartX;
            mYLength = mFinalY - mStartY;
            mCounter = 2;
        }
        float factor = 1f - (y - mAppHeaderHeight) / mLength;

        //Log.i("yy", dependency.getTop() + " >> " + y + " >> " + factor + " - " + mStartX + " " + mFinalX + " " + mStartY + " " + mFinalY);


        float newX = mStartX + factor * mXLength;
        float newY = mStartY + factor * mYLength;
        int newSize = (int) (mStartSize + factor * mSizeLength);

        child.setX(newX);
        child.setY(newY);

        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        lp.width = newSize;
        lp.height = newSize;
        child.setLayoutParams(lp);

        //child.setZ(99);

        return true;
    }

    public int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


}
