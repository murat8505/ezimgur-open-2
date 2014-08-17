package com.ezimgur.session;

import com.ezimgur.datacontract.AuthenticationToken;
import com.ezimgur.datacontract.Conversation;

import java.util.List;

/**
 * Created by mharris on 8/14/14.
 * © 2014 NCR Corporation
 */
public class SessionState {

    public AuthenticationToken token;

    public List<Conversation> conversations;
}
