package com.ezimgur.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

/**
 *
 * This code was created by Nir Hartman and is licensed under the MIT license.
 *
 * Please see https://github.com/nirhart/ParallaxScroll for reference.
 *
 */
public class ParallaxListView extends ListView {

    private ParallaxListViewHelper helper;

    public ParallaxListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public ParallaxListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    protected void init(Context context, AttributeSet attrs) {
        helper = new ParallaxListViewHelper(context, attrs, this);
        super.setOnScrollListener(helper);
    }

    public void addParallaxedHeaderView(View v) {
        ClippingRelativeLayout clippingLayout = new ClippingRelativeLayout(v.getContext());
        clippingLayout.addView(v);

        super.addHeaderView(clippingLayout);
        helper.addParallaxedHeaderView(clippingLayout);
    }

    public void addParallaxedHeaderView(View v, Object data, boolean isSelectable) {
        ClippingRelativeLayout clippingLayout = new ClippingRelativeLayout(v.getContext());
        clippingLayout.addView(v);

        super.addHeaderView(clippingLayout, data, isSelectable);
        helper.addParallaxedHeaderView(clippingLayout, data, isSelectable);
    }
}