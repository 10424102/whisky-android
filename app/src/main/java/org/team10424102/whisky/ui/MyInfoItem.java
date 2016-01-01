package org.team10424102.whisky.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.team10424102.whisky.R;
import org.team10424102.whisky.databinding.ItemMyInfoBinding;
import org.team10424102.whisky.databinding.ItemMyInfoNoIconBinding;
import org.team10424102.whisky.utils.DimensionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyInfoItem extends RightArrowBar {
    private static final float DEFAULT_LEFT_SPAN = 50;
    private Drawable mIcon;
    private String mCaption;
    private int mLeftSpan;
    private LinearLayout mContentLayout;
    private OnCommitListener mOnCommitListener;
    private String mContent;
    private boolean mEditable;
    private CharSequence[] mOptions;
    private int mLength;
    private String mRegex;

    private TextView mTextView;
    private EditText mEditText;
    private Spinner mSpinner;

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
                case R.styleable.MyInfoItem_content:
                    mContent = a.getString(attr);
                    break;
                case R.styleable.MyInfoItem_editable:
                    mEditable = a.getBoolean(attr, false);
                    break;
                case R.styleable.MyInfoItem_options:
                    mOptions = a.getTextArray(attr);
                    break;
                case R.styleable.MyInfoItem_length:
                    mLength = a.getInt(attr, 24);
                    break;
                case R.styleable.MyInfoItem_regex:
                    mRegex = a.getString(attr);
                    break;
            }
        }

        a.recycle();

    }


    @Override
    protected void onAttachedToWindow() {
        View root;

        if (mIcon == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            ItemMyInfoNoIconBinding binding = ItemMyInfoNoIconBinding.inflate(inflater, null, false);
            binding.setCaption(mCaption);
            binding.setLeftSpan(mLeftSpan);
            mContentLayout = binding.content;
            root = binding.getRoot();
        } else {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            ItemMyInfoBinding binding = ItemMyInfoBinding.inflate(inflater, null, false);
            binding.setCaption(mCaption);
            binding.setIcon(mIcon);
            binding.setLeftSpan(mLeftSpan);
            mContentLayout = binding.content;
            root = binding.getRoot();
        }

        if(mOptions!=null) mEditable = true;

        if (mEditable) {
            if (mOptions == null) {
                mEditText = new EditText(getContext());
                mEditText.setText(mContent);
                mEditText.setTextSize(13);
                mContentLayout.addView(mEditText);
            } else {
                mSpinner = new Spinner(getContext());
                mSpinner.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, mOptions));
                for(int i = 0;i<mOptions.length;i++) {
                    if (mOptions[i].equals(mContent)) {
                        mSpinner.setSelection(i);
                    }
                }
                mContentLayout.addView(mSpinner);
            }
        } else {
            mTextView = new TextView(getContext());
            mTextView.setText(mContent);
            mContentLayout.addView(mTextView);
        }

        removeAllViews();
        addView(root);
        super.onAttachedToWindow();
    }

    public void commit() {
        if (mOnCommitListener != null) mOnCommitListener.onCommit(mContentLayout);
    }

    public void setOnCommitListener(OnCommitListener listener) {
        mOnCommitListener = listener;
    }

    public interface OnCommitListener {
        void onCommit(ViewGroup contentRoot);
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;

        if (mEditable) {
            if (mOptions == null) {
                mEditText.setText(mContent);
            } else {
                for(int i = 0;i<mOptions.length;i++) {
                    if (mOptions[i].equals(mContent)) {
                        mSpinner.setSelection(i);
                    }
                }
            }
        } else {
            mTextView.setText(mContent);
        }
    }


}
