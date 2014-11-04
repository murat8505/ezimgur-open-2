package com.ezimgur.ui;

import android.os.Bundle;
import android.view.Window;

import com.ezimgur.R;
import com.ezimgur.ui.base.BaseActivity;
import com.ezimgur.ui.gallery.GalleryFragment;

/**
 * Created by mharris on 8/22/14.
 *
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //go to first fragment eventually here.
        goToFragment(new GalleryFragment());
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    @Override
    public int getContentViewId() {
        return R.layout.act_main;
    }
}
