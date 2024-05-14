package com.kaan.myapplication.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kaan.myapplication.R;


/**
 * File Description
 *
 * @author zhangja
 * @version 1.0
 * @since 2024/1/18
 */
public class FastScrollRecyclerView extends RecyclerView {

    private boolean mFastScroll = true;
    private int mScrollBarWidth;
    private boolean mScrollEnabled;
    private boolean mScrollBegin;
    private int mLastPosition;
    private int mItemCount;
    private int mDownY;

    public FastScrollRecyclerView(@NonNull Context context) {
        super(context);
        init();
    }

    public FastScrollRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FastScrollRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setLayoutManager(new LinearLayoutManager(getContext(), VERTICAL, false));
        mScrollBarWidth = 80;
    }

    public void setFastScrollEnabled(boolean enabled) {
        mFastScroll = enabled;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        if (!mFastScroll || getAdapter() == null) {
            return super.dispatchTouchEvent(e);
        }
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int left = getWidth() - mScrollBarWidth;
                int x = (int) e.getX();
                if (x >= left && x < getWidth()) {
                    setThumbDrawable(R.drawable.scrollbar_thumb_press_layer);
                    mDownY = (int) e.getY();
                    mScrollEnabled = true;
                    mItemCount = getAdapter().getItemCount();
                    super.dispatchTouchEvent(e);
                } else {
                    mScrollEnabled = false;
                    mScrollBegin = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mScrollEnabled) {
                    int y = (int) e.getY();
                    if (Math.abs(y - mDownY) > 10 || mScrollBegin) {
                        mScrollBegin = true;
                        float value = e.getY() / getHeight();
                        if (mItemCount > 0) {
                            int position = (int) (value * mItemCount);
                            if (position != mLastPosition) {
                                mLastPosition = position;
                                scrollToPosition(mLastPosition);
                            }
                        }
                        return false;
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                setThumbDrawable(R.drawable.scrollbar_thumb_normal_layer);
                mScrollEnabled = false;
                mScrollBegin = false;
        }
        return super.dispatchTouchEvent(e);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        if (mScrollBegin) {
            return false;
        }
        return super.onInterceptTouchEvent(e);
    }

    private static int dip2px(float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private void setThumbDrawable(int drawableRes) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), drawableRes);
        setVerticalScrollbarThumbDrawable(drawable);
    }
}