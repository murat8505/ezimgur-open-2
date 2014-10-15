package com.ezimgur.ui.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by mharris on 8/16/14.
 *
 */
public class UiBuilder {

    public static View inflate( Context context, int resourceId) {
        LayoutInflater li = ( LayoutInflater )context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        return li.inflate( resourceId, null);
    }
}
