package com.ezimgur.service.request;

import com.ezimgur.api.AuthenticationApi;
import com.ezimgur.datacontract.AuthenticationToken;

import javax.inject.Inject;

/**
 * Created by mharris on 8/14/14.
 * © 2014 NCR Corporation
 */
public class RefreshTokenRequest extends BaseRequest<AuthenticationToken> {

    @Inject
    protected AuthenticationApi authenticationApi;

    private String refreshToken;

    public RefreshTokenRequest(String refreshToken) {
        super(AuthenticationToken.class);

        this.refreshToken = refreshToken;
    }

    @Override
    public AuthenticationToken loadDataFromNetwork() throws Exception {
        AuthenticationToken token = authenticationApi.refreshAuthenticationToken(refreshToken);

        authenticationApi.setCurrentAuthenticationToken(token);

        return token;
    }
}
