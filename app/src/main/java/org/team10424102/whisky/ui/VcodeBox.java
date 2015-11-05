package org.team10424102.whisky.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import org.team10424102.whisky.R;
import org.team10424102.whisky.utils.DimensionUtils;

/**
 * Created by yy on 11/14/15.
 */
public class VcodeBox extends View {
    private int mGap;
    private int mLength;
    private Paint mTextPaint;
    private Paint mBoxPaint;

    public VcodeBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.VcodeBox,
                0, 0);
        try {
            mGap = a.getDimensionPixelSize(R.styleable.VcodeBox_gap,
                    (int) DimensionUtils.dp2px(10, context.getResources()));
            mLength = a.getInt(R.styleable.VcodeBox_lenght, 4);
        } finally {
            a.recycle();
        }
    }

    private void init() {
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLUE);

        mBoxPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBoxPaint.setColor(Color.BLACK);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mBoxPaint);
    }
}
