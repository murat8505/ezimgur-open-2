package com.ezimgur.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ezimgur.R;
import com.ezimgur.session.ImgurSession;
import com.ezimgur.ui.base.BaseFragment;
import com.ezimgur.ui.login.LoginActivity;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * Created by mharris on 8/14/14.
 * © 2014 NCR Corporation
 */
public class MenuFragment extends BaseFragment {

    @Inject
    protected ImgurSession session;

    @InjectView(R.id.frag_menu_lv_items)
    protected ListView listItems;
    @InjectView(R.id.frag_menu_ll_login_button)
    protected LinearLayout loginButton;
    @InjectView(R.id.frag_menu_tv_login_status)
    protected TextView txtLoginStatus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflate(inflater, container, R.layout.frag_menu);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (session.isAuthenticated()) {
                    //todo:wire up account page.
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        setLoginText();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        setLoginText();
    }

    private void setLoginText() {
        if (session.isAuthenticated()) {
            txtLoginStatus.setText(session.getAccountName());
        } else {
            txtLoginStatus.setText(R.string.menuNotLoggedIn);
        }
    }
}
