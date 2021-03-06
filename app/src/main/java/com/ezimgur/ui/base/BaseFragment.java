package com.ezimgur.ui.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
 *
 */
public class BaseFragment extends Fragment {

    @Inject
    protected Bus bus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EzApplication.app().inject(this);
    }

    protected View inflate(LayoutInflater inflater, ViewGroup container, int layoutId) {
        View view = inflater.inflate(layoutId, container, false);

        ButterKnife.inject(this, view);

        return view;
    }

    public BaseActivity activity(){
        return (BaseActivity) getActivity();
    }
}
