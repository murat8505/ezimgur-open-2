package com.ezimgur.ui;

import android.os.Bundle;

import com.ezimgur.R;
import com.ezimgur.ui.base.BaseActivity;

/**
 * Created by mharris on 8/22/14.
 * © 2014 NCR Corporation
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //go to first fragment eventually here.
    }

    @Override
    public int getContentViewId() {
        return R.layout.act_main;
    }
}
