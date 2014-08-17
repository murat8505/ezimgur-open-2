package com.ezimgur.api.impl.conversation;

import com.ezimgur.api.ConversationApi;
import com.ezimgur.api.exception.ApiException;
import com.ezimgur.api.impl.ApiBase;
import com.ezimgur.api.impl.conversation.request.GetConversationRequest;
import com.ezimgur.api.impl.conversation.request.GetConversationsRequest;
import com.ezimgur.datacontract.Conversation;

import java.util.List;

/**
 * Created by mharris on 7/11/14.
 *
 */
public class ConversationApiImpl extends ApiBase implements ConversationApi {

    @Override
    public List<Conversation> getConversations() throws ApiException {
        GetConversationsRequest request = new GetConversationsRequest();
        submitApiRequest(request);

        return request.getItemToReceive().data;
    }

    @Override
    public Conversation getConversation(int conversationId, int page) throws ApiException  {
        GetConversationRequest request = new GetConversationRequest(conversationId, page);

         submitApiRequest(request);

        return request.getItemToReceive().data;
    }

    @Override
    public void sendMessage(String reciepient, String message) throws ApiException {

    }

    @Override
    public void deleteConversation(String conversationId) throws ApiException {

    }
}
