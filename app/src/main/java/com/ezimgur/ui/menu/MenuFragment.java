package com.ezimgur.ui.menu;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ezimgur.R;
import com.ezimgur.event.AuthenticationChangedEvent;
import com.ezimgur.session.ImgurSession;
import com.ezimgur.ui.account.AccountFragment;
import com.ezimgur.ui.base.BaseFragment;
import com.ezimgur.ui.login.LoginActivity;
import com.ezimgur.ui.menu.adapter.NavigationMenuAdapter;
import com.ezimgur.ui.message.MessagesFragment;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

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

    private static final String TAG = "EzImgur.MenuFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();

        bus.unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflate(inflater, container, R.layout.frag_menu);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setLoginText();

        final List<NavigationMenuItem> navItems = new ArrayList<NavigationMenuItem>();
        navItems.add(new NavigationMenuItem("messages", MessagesFragment.class));

        final NavigationMenuAdapter menuAdapter = new NavigationMenuAdapter(navItems);
        listItems.setAdapter(menuAdapter);

        listItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final NavigationMenuItem navigationMenuItem = menuAdapter.getItem(position);

                goToFragment(navigationMenuItem.targetFragment);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        bus.register(this);

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
            goToFragment(AccountFragment.class);
        } else {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
    }

    private void goToFragment(final Class<? extends Fragment> fragmentClass) {

        activity().toggleNavigationMenu();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    activity().goToFragment(fragmentClass.newInstance());
                } catch (Exception e) {
                    Log.e(TAG, "unable to create the fragment you are trying to navigate to, see cause.", e);
                }
            }
        }, 300);
    }

    private void setLoginText() {
        if (session.isAuthenticated()) {
            txtLoginStatus.setText(session.getAccountName());
        } else {
            txtLoginStatus.setText(R.string.menuNotLoggedIn);
        }
    }

}
