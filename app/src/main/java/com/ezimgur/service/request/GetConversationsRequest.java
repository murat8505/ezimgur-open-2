package com.ezimgur.service.request;

import com.ezimgur.api.ConversationApi;
import com.ezimgur.datacontract.Conversation;
import com.ezimgur.session.ImgurSession;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by mharris on 8/16/14.
 *
 */
public class GetConversationsRequest extends BaseRequest<GetConversationsRequest.Conversations> {

    @Inject
    protected ConversationApi conversationApi;

    @Inject
    protected ImgurSession session;

    public GetConversationsRequest() {
        super(Conversations.class);
    }

    @Override
    public Conversations loadDataFromNetwork() throws Exception {
        Conversations conversations = new Conversations();
        conversations.addAll(conversationApi.getConversations());

        session.setConversations(conversations);

        return conversations;
    }

    public static class Conversations extends ArrayList<Conversation> {

    }
}
