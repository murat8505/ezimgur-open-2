package com.ezimgur.api;

import com.ezimgur.api.exception.ApiException;
import com.ezimgur.datacontract.Conversation;

import java.util.List;

/**
 * Created by mharris on 7/11/14.
 *
 * New conversations api.
 */
public interface ConversationApi {

    public List<Conversation> getConversations() throws ApiException;

    public Conversation getConversation(int conversationId, int page) throws ApiException ;

    public void sendMessage(String reciepient, String message) throws ApiException ;

    public void deleteConversation(int conversationId) throws ApiException ;

}
