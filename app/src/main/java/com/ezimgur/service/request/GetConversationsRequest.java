package com.ezimgur.service.request;

import com.ezimgur.api.ConversationApi;
import com.ezimgur.datacontract.Conversation;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by mharris on 8/16/14.
 * © 2014 NCR Corporation
 */
public class GetConversationsRequest extends BaseRequest<GetConversationsRequest.Conversations> {

    @Inject
    protected ConversationApi conversationApi;

    public GetConversationsRequest() {
        super(Conversations.class);
    }

    @Override
    public Conversations loadDataFromNetwork() throws Exception {
        Conversations conversations = new Conversations();
        conversations.addAll(conversationApi.getConversations());

        return conversations;
    }

    public static class Conversations extends ArrayList<Conversation> {

    }
}
