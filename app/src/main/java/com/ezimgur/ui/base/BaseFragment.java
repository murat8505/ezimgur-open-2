package com.ezimgur.ui.base;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ezimgur.EzApplication;
import com.ezimgur.R;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by mharris on 8/14/14.
 * © 2014 NCR Corporation
 */
public class BaseFragment extends Fragment {

    @Inject
    protected Bus bus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EzApplication.app().inject(this);

        bus.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        bus.unregister(this);
    }

    protected View inflate(LayoutInflater inflater, ViewGroup container, int layoutId) {
        View view = inflater.inflate(R.layout.frag_menu, container, false);

        ButterKnife.inject(this, view);

        return view;
    }
}
