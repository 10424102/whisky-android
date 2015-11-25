package org.team10424102.whisky.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.team10424102.whisky.R;
import org.team10424102.whisky.databinding.ItemMyInfoBinding;
import org.team10424102.whisky.databinding.ItemMyInfoNoImageBinding;
import org.team10424102.whisky.utils.DimensionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyInfoItem extends RightArrowBar {
    private static final float DEFAULT_LEFT_SPAN = 60;
    private Drawable mIcon;
    private String mCaption;
    private int mLeftSpan;
    private LinearLayout mContent;
    private OnCommitListener mOnCommitListener;

    public MyInfoItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        final TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.MyInfoItem,
                0, 0);
        final int N = a.getIndexCount();
        for (int i = 0; i < N; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.MyInfoItem_caption:
                    mCaption = a.getString(attr);
                    break;
                case R.styleable.MyInfoItem_leftSpan:
                    mLeftSpan = a.getDimensionPixelSize(attr, DimensionUtils.dp2px(DEFAULT_LEFT_SPAN));
                    break;
                case R.styleable.MyInfoItem_myIcon:
                    mIcon = a.getDrawable(attr);
                    break;
                case R.styleable.MyInfoItem_onCommit:
                    final String handlerName = a.getString(attr);
                    if (handlerName != null) {
                        setOnCommitListener(new OnCommitListener() {
                            private Method mHandler;

                            @Override
                            public void onCommit(ViewGroup contentRoot) {
                                if (mHandler == null) {
                                    try {
                                        mHandler = getContext().getClass().getMethod(handlerName, ViewGroup.class);
                                    } catch (NoSuchMethodException e) {
                                        throw new IllegalStateException("没找到方法 " + handlerName);
                                    }
                                }

                                try {
                                    mHandler.invoke(getContext(), contentRoot);
                                } catch (IllegalAccessException e) {
                                    throw new IllegalStateException("没办法执行非 public 方法");
                                } catch (InvocationTargetException e) {
                                    throw new IllegalStateException("没法执行方法" + handlerName);
                                }
                            }
                        });
                    }
                    break;
            }
        }

        a.recycle();

    }


    @Override
    protected void onAttachedToWindow() {

        View root = null;
        if (mIcon == null) {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            ItemMyInfoNoImageBinding binding = ItemMyInfoNoImageBinding.inflate(inflater, null, false);
            binding.setCaption(mCaption);
            binding.setLeftSpan(mLeftSpan);
            mContent = binding.content;
            root = binding.getRoot();

        } else {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            ItemMyInfoBinding binding = ItemMyInfoBinding.inflate(inflater, null, false);
            binding.setCaption(mCaption);
            binding.setIcon(mIcon);
            binding.setLeftSpan(mLeftSpan);
            mContent = binding.content;
            root = binding.getRoot();

        }


        while (getChildCount() > 0) {
            View v = getChildAt(0);
            removeViewAt(0);
            mContent.addView(v);
        }


        removeAllViews();

        addView(root);

        super.onAttachedToWindow();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

//        LayoutInflater inflater = LayoutInflater.from(getContext());
//        ItemMyInfoBinding binding = ItemMyInfoBinding.inflate(inflater, getContentRoot(), true);
//        binding.setCaption(mCaption);
//        binding.setIcon(mIcon);
//        binding.setLeftSpan(mLeftSpan);
//        mContent = binding.content;
//
//
//        Log.i("yy", getContentRoot().getChildCount()+ "aaa");
//
//
//        while (getChildCount() > 0) {
//            View v = getChildAt(0);
//            removeViewAt(0);
//            mContent.addView(v);
//        }
//
//        Log.i("yy", mContent.getChildCount()+ "bbb");

        super.onLayout(changed, left, top, right, bottom);

    }

    public void commit() {
        if (mOnCommitListener != null) mOnCommitListener.onCommit(mContent);
    }

    public void setOnCommitListener(OnCommitListener listener) {
        mOnCommitListener = listener;
    }

    public interface OnCommitListener {
        void onCommit(ViewGroup contentRoot);
    }
}
