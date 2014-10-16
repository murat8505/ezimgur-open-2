package com.ezimgur.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.widget.RelativeLayout;

/**
 * Created by mharris on 7/31/14.
 *
 * Code from github...https://github.com/nirhart/ParallaxScroll/issues/7
 */
public class ClippingRelativeLayout extends RelativeLayout {

    private int offset;

    public ClippingRelativeLayout(Context context) {
        super(context);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.clipRect(new Rect(getLeft(), getTop(), getRight(), getBottom() + offset));
        super.dispatchDraw(canvas);
    }

    public void setClipY(int offset) {
        this.offset = offset;
        invalidate();
    }
}