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
import com.ezimgur.event.AuthenticationChangedEvent;
import com.ezimgur.session.ImgurSession;
import com.ezimgur.ui.base.BaseFragment;
import com.ezimgur.ui.login.LoginActivity;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bus.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        bus.unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflate(inflater, container, R.layout.frag_menu);

        setLoginText();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        setLoginText();
    }

    @Subscribe
    public void onAuthenticationChanged(AuthenticationChangedEvent event) {
        setLoginText();
    }

    @OnClick(R.id.frag_menu_ll_login_button)
    @SuppressWarnings("unused")
    public void onLoginClicked() {
        if (session.isAuthenticated()) {
            //todo:wire up account page.
        } else {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
    }

    private void setLoginText() {
        if (session.isAuthenticated()) {
            txtLoginStatus.setText(session.getAccountName());
        } else {
            txtLoginStatus.setText(R.string.menuNotLoggedIn);
        }
    }

}
