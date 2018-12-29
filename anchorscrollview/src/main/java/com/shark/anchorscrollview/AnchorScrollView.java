package com.shark.anchorscrollview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class AnchorScrollView extends NestedScrollView {
    private int scrollOffset = 0;
    private View anchorView;
    private Set<View> anchorViewSet;
    private OnNestedScrollViewChangedListener onNestedScrollViewChangedListener;
    private OnScrollChangeListener customerScrollChangeListener;
    private OnScrollChangeListener onScrollChangeListener = new OnScrollChangeListener() {
        @Override
        public void onScrollChange(NestedScrollView nestedScrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            if (customerScrollChangeListener != null)
                customerScrollChangeListener.onScrollChange(nestedScrollView, scrollX, scrollY, oldScrollX, oldScrollY);
            //如果当前滚动Y位于anchorView返回内，直接return不做后续操作，
            if (anchorView != null && scrollY >= anchorView.getTop() - scrollOffset && scrollY < anchorView.getBottom()-scrollOffset)
                return;
            //查找最新锚点View并跟新当前anchorView
            View view = findAnchor(getScrollY());
            if (view == null) return;
            anchorView = view;

            if (onNestedScrollViewChangedListener == null) return;

            if (scrollY == 0) onNestedScrollViewChangedListener.onScrollStop();

            if ((scrollY + getMeasuredHeight()) >= getChildAt(0).getMeasuredHeight()) {
                onNestedScrollViewChangedListener.onScrollToBottom();
            }

            if (anchorView == null) return;
            onNestedScrollViewChangedListener.onAnchor(anchorView);
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
                scrollTo(0, view.getTop() - scrollOffset);
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
            if (scrollY < view.getTop() - scrollOffset) continue;
            if (scrollY >= view.getBottom() - scrollOffset) continue;
            return view;
        }
        return null;
    }

    public void setScrollOffset(int scrollOffset) {
        this.scrollOffset = scrollOffset;
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
