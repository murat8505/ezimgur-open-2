package com.ezimgur.service.request;

import com.ezimgur.api.ConversationApi;

import javax.inject.Inject;

/**
 * Created by mharris on 8/17/14.
 *
 */
public class CreateMessageRequest extends BaseRequest<Object> {

    @Inject
    protected ConversationApi conversationApi;

    private String recipient;
    private String message;

    public CreateMessageRequest(String recipient, String message) {
        super(Object.class);

        this.recipient = recipient;
        this.message = message;
    }

    @Override
    public Object loadDataFromNetwork() throws Exception {

        conversationApi.sendMessage(recipient, message);
        return null;
    }
}
