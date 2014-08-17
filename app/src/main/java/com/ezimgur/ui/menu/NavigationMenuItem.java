package com.ezimgur.ui.menu;

import android.app.Activity;

/**
 * Created by mharris on 8/16/14.
 * © 2014 NCR Corporation
 */
public class NavigationMenuItem {

    public String title;
    public Class<? extends Activity> targetActivity;

    public NavigationMenuItem(String title, Class<? extends Activity> targetActivity) {
        this.title = title;
        this.targetActivity = targetActivity;
    }
}
