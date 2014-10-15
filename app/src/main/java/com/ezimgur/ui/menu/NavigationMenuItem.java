package com.ezimgur.ui.menu;

import android.app.Fragment;

/**
 * Created by mharris on 8/16/14.
 *
 */
public class NavigationMenuItem {

    public String title;
    public Class<? extends Fragment> targetFragment;

    public NavigationMenuItem(String title, Class<? extends Fragment> targetFragment) {
        this.title = title;
        this.targetFragment = targetFragment;
    }
}
