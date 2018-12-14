package com.shark.anchorscrollview.demo;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.LinearLayout;

import java.util.logging.Handler;

public class TRSWebView extends WebView {
    private int lastContentHeight = 0;



    public TRSWebView(Context context) {
        this(context, null);
        addJavascriptInterface(this, "Resize");
    }

    public TRSWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        post(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        })
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//                Log.e("shark", "contentChange height=" + getContentHeight());

        //        Log.e("www", "contentChange height=" + canvas.getHeight());

//        if (getContentHeight() != lastContentHeight) {
//            lastContentHeight = getContentHeight();
//            Log.e("www", "contentChange height=" + getContentHeight());
//        }
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (event.getAction()==MotionEvent.ACTION_UP){
//            computeHeight();
//        }
//
//        return super.onTouchEvent(event);
//    }
//    public void computeHeight() {
//        loadUrl("javascript:Resize.fetchHeight(document.body.getBoundingClientRect().height)");
//    }
//
//    @JavascriptInterface
//    public void fetchHeight(final float height) {
//        Log.e("shark", "fetchHeight:" + height);
//        final int newHeight = (int) (height * getResources().getDisplayMetrics().density);
//        Log.e("shark", "fetchHeight2:" + newHeight);
//       post(new Runnable() {
//            @Override
//            public void run() {
//                if (getLayoutParams() instanceof LinearLayout.LayoutParams) {
//                    Log.e("Shark", "View height:"+getHeight());
//                    LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) getLayoutParams();
//                    linearParams.height = newHeight;
//                    setLayoutParams(linearParams);
//                   postInvalidate();
//
//                    Log.e("Shark", "Layout height:"+linearParams.height);
//                    Log.e("Shark", "View height:"+getHeight());
//                    Log.e("Shark", "Content height:"+getContentHeight());
//
//                }
//            }
//        });
//    }
}

