package com.ezimgur.service.request;

import com.ezimgur.api.ConversationApi;
import com.ezimgur.datacontract.Conversation;

import javax.inject.Inject;

/**
 * Created by mharris on 8/16/14.
 *
 */
public class GetConversationRequest extends BaseRequest<Conversation> {

    @Inject
    protected ConversationApi conversationApi;

    private int id;
    private int page;

    public GetConversationRequest(int id, int page) {
        super(Conversation.class);
        this.id = id;
        this.page = page;
    }

    @Override
    public Conversation loadDataFromNetwork() throws Exception {
        return conversationApi.getConversation(id, page);
    }
}
