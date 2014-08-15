package com.ezimgur.ui.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.ezimgur.R;
import com.ezimgur.api.AuthenticationApi;
import com.ezimgur.api.exception.ApiException;
import com.ezimgur.datacontract.AuthenticationToken;
import com.ezimgur.datacontract.OAuthRequestType;
import com.ezimgur.session.ImgurSession;
import com.ezimgur.ui.base.BaseActivity;

import javax.inject.Inject;

/**
 * Created by mharris on 8/14/14.
 * © 2014 NCR Corporation
 */
public class LoginActivity extends BaseActivity {

    public static final String	OAUTH_CALLBACK_URL = "oauthflow-imgur://callback";
    public static boolean isLoggingIn = false;
    public static boolean isLoggedIn = false;
    private String authUrl;

    @Inject
    protected AuthenticationApi authenticationApi;
    @Inject
    protected ImgurSession session;

    private static final String TAG = "EzImgur.AccountLogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate logginIn = "+isLoggingIn + " isLoggedIn = " + isLoggedIn);

        if (!session.isAuthenticated() && !isLoggingIn ) {
            isLoggingIn = false;
            showLoginPrompt();
        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.act_login;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume logginIn = "+isLoggingIn + " isLoggedIn = " + isLoggedIn);

        if (isLoggingIn) {
            final Uri uri = getIntent().getData();
            final LoginActivity act = this;

            if (uri != null) {
                String uriPath = uri.toString();
                if (uriPath.startsWith(OAUTH_CALLBACK_URL))
                {
                    try {
                        AuthenticationToken token = authenticationApi.getTokenFromTokenResponse(uri.getFragment());
                        session.setAuthenticationToken(token);
                    } catch (ApiException e) {
                        Log.d(TAG, "error getting token", e);
                    }

                    isLoggedIn = false;
                    isLoggingIn = false;
                    finish();
                }
                else {
                    isLoggedIn = false;
                    isLoggingIn = false;
                }
            } else  {
                showLoginPrompt();
            }
        }

    }

    private void showLoginPrompt() {

        try {
            authUrl = authenticationApi.getOAuthUrl(OAUTH_CALLBACK_URL, OAuthRequestType.TOKEN);
        } catch (ApiException e) {
            Log.e(TAG, "unable to get OAUTH url", e);
        }

        Log.d(TAG, "Showing login prompt at url:" + authUrl);

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(authUrl));
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_FROM_BACKGROUND);
        startActivityForResult(intent, 0);
        isLoggingIn = true;
    }

}
