package com.shark.anchorscrollview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;

import java.util.HashSet;
import java.util.Set;

public class AnchorScrollView extends NestedScrollView {
    private Set<View> anchorViewSet;
    private OnNestedScrollViewChangedListener onNestedScrollViewChangedListener;
    private OnScrollChangeListener customerScrollChangeListener;
    private OnScrollChangeListener onScrollChangeListener = new OnScrollChangeListener() {
        @Override
        public void onScrollChange(NestedScrollView nestedScrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            removeCallbacks(scrollStateChangeTask);
            postDelayed(scrollStateChangeTask, 100);

            if (customerScrollChangeListener == null) return;
            customerScrollChangeListener.onScrollChange(nestedScrollView, scrollX, scrollY, oldScrollX, oldScrollY);
//            if (Math.abs(scrollY-oldScrollY)<=2||scrollY==0||(scrollY+getMeasuredHeight())==getChildAt(0).getMeasuredHeight()) SLog.e(this, "Scroll stop");

        }
    };
    private Runnable scrollStateChangeTask = new Runnable() {
        @Override
        public void run() {
            if (onNestedScrollViewChangedListener == null) return;
            onNestedScrollViewChangedListener.onScrollStop();
            onNestedScrollViewChangedListener.onAnchor(findAnchor(getScrollY()));

            if ((getScrollY() + getMeasuredHeight()) < getChildAt(0).getMeasuredHeight()) return;
            onNestedScrollViewChangedListener.onScrollToBottom();
        }
    };

    public AnchorScrollView(@NonNull Context context) {
        super(context);
        init();

    }

    public AnchorScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnchorScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    private void init() {
        super.setOnScrollChangeListener(onScrollChangeListener);
        anchorViewSet = new HashSet<>();
    }

    public void anchorView(final View view) {
        post(new Runnable() {
            @Override
            public void run() {
                scrollTo(0, view.getTop());
            }
        });
    }

    public void addAnchorView(View... view) {
        if (view == null) return;
        for (View v : view) {
            anchorViewSet.add(v);
        }
    }

    public void removeAnchorView(View view) {
        anchorViewSet.remove(view);
    }

    private View findAnchor(int scrollY) {
        for (View view : anchorViewSet) {
            if (scrollY < view.getTop()) continue;
            if (scrollY >= view.getBottom()) continue;
            return view;
        }
        return null;
    }

    @Override
    public void setOnScrollChangeListener(OnScrollChangeListener listener) {
        customerScrollChangeListener = listener;
    }

    public OnNestedScrollViewChangedListener getOnNestedScrollViewChangedListener() {
        return onNestedScrollViewChangedListener;
    }

    public void setOnNestedScrollViewChangedListener(OnNestedScrollViewChangedListener onNestedScrollViewChangedListener) {
        this.onNestedScrollViewChangedListener = onNestedScrollViewChangedListener;
    }

    public interface OnNestedScrollViewChangedListener {
        void onAnchor(View view);

        void onScrollStop();

        void onScrollToBottom();
    }
}
