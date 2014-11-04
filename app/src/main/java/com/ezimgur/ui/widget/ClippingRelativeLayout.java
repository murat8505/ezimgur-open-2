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

    private boolean disabled;
    private int offset;


    public ClippingRelativeLayout(Context context) {
        super(context);
    }

    public void disableClipping(){
        this.disabled = true;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (!disabled){
            canvas.clipRect(new Rect(getLeft(), getTop(), getRight(), getBottom() + offset));
        }

        super.dispatchDraw(canvas);
    }

    public void setClipY(int offset) {
        this.offset = offset;

        if (!disabled) {
            invalidate();
        }
    }
}