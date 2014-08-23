package com.ezimgur.ui.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ezimgur.R;
import com.ezimgur.session.ImgurSession;
import com.ezimgur.ui.base.BaseFragment;

import javax.inject.Inject;

/**
 * Created by mharris on 8/22/14.
 * © 2014 NCR Corporation
 */
public class AccountFragment extends BaseFragment {

    @Inject
    protected ImgurSession session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflate(inflater, container, R.layout.frag_account);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle(session.getAccountName());
    }
}
