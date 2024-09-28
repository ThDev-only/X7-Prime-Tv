package com.ultra.vision.adapter.config;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;

public class CenterlZoomLayoutManager extends LinearLayoutManager {

    private static final float MIN_SCALE = 0.8f;

    public CenterlZoomLayoutManager(Context context) {
        super(context);
    }

    public CenterlZoomLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public CenterlZoomLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        LinearSmoothScroller scroller = new LinearSmoothScroller(recyclerView.getContext()) {
            @Override
            protected int getHorizontalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_ANY;
            }

            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_ANY;
            }
        };
        scroller.setTargetPosition(position);
        startSmoothScroll(scroller);
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int scrolled = super.scrollHorizontallyBy(dx, recycler, state);
        float midpoint = getWidth() / 2.0f;

        for (int i = 0; i < getChildCount(); i++) {
            float childMidpoint = (getDecoratedRight(getChildAt(i)) + getDecoratedLeft(getChildAt(i))) / 2.0f;
            float distanceFromCenter = Math.abs(midpoint - childMidpoint);
            float scale = 1.0f - MIN_SCALE * (distanceFromCenter / midpoint);
            getChildAt(i).setScaleX(scale);
            getChildAt(i).setScaleY(scale);
        }

        return scrolled;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int scrolled = super.scrollVerticallyBy(dy, recycler, state);
        float midpoint = getHeight() / 2.0f;

        for (int i = 0; i < getChildCount(); i++) {
            float childMidpoint = (getDecoratedBottom(getChildAt(i)) + getDecoratedTop(getChildAt(i))) / 2.0f;
            float distanceFromCenter = Math.abs(midpoint - childMidpoint);
            float scale = 1.0f - MIN_SCALE * (distanceFromCenter / midpoint);
            getChildAt(i).setScaleX(scale);
            getChildAt(i).setScaleY(scale);
        }

        return scrolled;
    }
}
