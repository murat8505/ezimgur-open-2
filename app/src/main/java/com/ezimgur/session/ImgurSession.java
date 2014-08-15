package com.ezimgur.session;

import com.ezimgur.datacontract.AuthenticationToken;

/**
 * Created by mharris on 8/14/14.
 * © 2014 NCR Corporation
 */
public interface ImgurSession {

    void onLifeCyclePause();

    boolean isAuthenticated();

    void setAuthenticationToken(AuthenticationToken token);

    boolean isAuthenticationExpired();

    String getAccountName();

    AuthenticationToken getAuthenticationToken();

    void logoutUser();
}
