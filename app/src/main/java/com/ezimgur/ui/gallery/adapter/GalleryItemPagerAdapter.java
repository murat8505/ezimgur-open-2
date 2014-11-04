package com.ezimgur.ui.gallery.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ezimgur.ui.gallery.GalleryItemFragment;

/**
 * Created by mharris on 10/15/14.
 *
 */
public class GalleryItemPagerAdapter extends FragmentStatePagerAdapter {

    private int totalCount;

    public GalleryItemPagerAdapter(FragmentManager fm, int totalCount) {
        super(fm);
        this.totalCount = totalCount;
    }

    @Override
    public Fragment getItem(int position) {
        return GalleryItemFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return totalCount;
    }
}
