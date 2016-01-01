package com.energysh.drawshow.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

import com.energysh.drawshow.Globals;

/**
 * Created by Administrator on 2015/12/28.
 */
public class CustomHorizontalScrollView extends HorizontalScrollView {
    public CustomHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(Globals.mBIntercepEvent)
            return super.onInterceptTouchEvent(ev);
        else
            return false;
    }
}
