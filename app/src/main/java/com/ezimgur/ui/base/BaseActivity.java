package com.ezimgur.ui.base;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.ezimgur.EzApplication;
import com.ezimgur.R;
import com.ezimgur.datacontract.AuthenticationToken;
import com.ezimgur.event.AuthenticationChangedEvent;
import com.ezimgur.service.RequestService;
import com.ezimgur.service.request.RefreshTokenRequest;
import com.ezimgur.session.ImgurSession;
import com.ezimgur.ui.menu.MenuFragment;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by mharris on 8/14/14.
 * © 2014 NCR Corporation
 */
public abstract class BaseActivity extends Activity {

    protected SpiceManager requestService = new SpiceManager(RequestService.class);

    @Inject
    protected ImgurSession session;
    @Inject
    protected Bus bus;

    protected boolean isRefreshingToken;

    @InjectView(R.id.drawer_layout)
    protected DrawerLayout drawerLayout;
    @InjectView(R.id.left_drawer)LinearLayout drawerMenuLayout;
    protected FrameLayout contentFrame;
    private ActionBarDrawerToggle drawerToggle;

    private static final String TAG = "EzImgur.BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EzApplication.app().inject(this);

        setupMenu();
    }

    /**
     * Loads the navigation menu. The actual content of the activity will be what is passed in by getContentViewId from
     * the child activity class.
     */
    private void setupMenu() {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        View baseView = View.inflate(this, R.layout.act_base, null);
        contentFrame = (FrameLayout) baseView.findViewById(R.id.content_frame);
        View.inflate(this, getContentViewId(), contentFrame);

        setContentView(baseView);
        ButterKnife.inject(this);

        MenuFragment menuFragment = new MenuFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.left_drawer, menuFragment);
        transaction.commit();

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open,
                R.string.drawer_close)
        {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getSupportActionBar().setTitle("ezimgur");
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getSupportActionBar().setTitle("menu");
            }

        };

        drawerLayout.setDrawerListener(drawerToggle);

        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    protected void onStart() {
        super.onStart();

        requestService.start(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        refreshAuthenticationTokenIfNeeded();
    }

    @Override
    protected void onPause() {
        super.onPause();

        session.onLifeCyclePause();
    }

    @Override
    protected void onStop() {
        try {
            requestService.shouldStop();
        } catch (IllegalArgumentException e) {
            Log.e(TAG, e.getMessage());
        }
        super.onStop();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            if (drawerLayout.isDrawerOpen(drawerMenuLayout))
                drawerLayout.closeDrawer(drawerMenuLayout);
            else
                drawerLayout.openDrawer(drawerMenuLayout);
        }

        return super.onOptionsItemSelected(item);
    }

    public SpiceManager getRequestService() {
        return this.requestService;
    }

    public void refreshAuthenticationTokenIfNeeded() {
        if (session.isAuthenticationExpired() && !isRefreshingToken) {
            AuthenticationToken token = session.getAuthenticationToken();
            isRefreshingToken = true;

            requestService.execute(new RefreshTokenRequest(token.refreshToken), authListener);
        }
    }

    public void goToChildFragment(int containerId, Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(containerId, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private RequestListener<AuthenticationToken> authListener = new RequestListener<AuthenticationToken>() {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            isRefreshingToken = false;
        }

        @Override
        public void onRequestSuccess(AuthenticationToken authenticationToken) {
            isRefreshingToken = false;
            session.setAuthenticationToken(authenticationToken);
            bus.post(new AuthenticationChangedEvent());

        }
    };

    public abstract int getContentViewId();
}
