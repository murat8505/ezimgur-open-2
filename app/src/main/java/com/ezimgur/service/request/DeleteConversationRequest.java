package com.ezimgur.service.request;

import com.ezimgur.api.ConversationApi;

import javax.inject.Inject;

/**
 * Created by mharris on 8/17/14.
 *
 */
public class DeleteConversationRequest extends BaseRequest<Object> {

    @Inject
    ConversationApi conversationApi;

    private int conversationId;

    public DeleteConversationRequest(int conversationId) {
        super(Object.class);
        this.conversationId = conversationId;
    }

    @Override
    public Object loadDataFromNetwork() throws Exception {
        conversationApi.deleteConversation(conversationId);
        return null;
    }
}
