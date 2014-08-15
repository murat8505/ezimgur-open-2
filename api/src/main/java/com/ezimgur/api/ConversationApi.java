package com.ezimgur.api;

import com.ezimgur.api.exception.ApiException;
import com.ezimgur.datacontract.Message;

import java.util.List;

/**
 * Created by mharris on 7/11/14.
 *
 * New conversations api.
 */
public interface ConversationApi {

    public List<Message> getConversations() throws ApiException;

    public Message getConversation(String messageId) throws ApiException ;

    public void sendMessage(String reciepient, String message) throws ApiException ;

    public void deleteConversation(String conversationId) throws ApiException ;

}
