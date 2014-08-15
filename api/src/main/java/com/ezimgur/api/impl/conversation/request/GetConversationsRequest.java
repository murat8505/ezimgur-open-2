package com.ezimgur.api.impl.conversation.request;

import com.ezimgur.api.ImgurApiConstants;
import com.ezimgur.api.http.ApiGetRequest;
import com.ezimgur.datacontract.Basic;
import com.ezimgur.datacontract.Message;

import java.util.List;

/**
 * Created by mharris on 7/11/14.
 */
public class GetConversationsRequest extends ApiGetRequest<GetConversationsRequest.GetMessagesResponse> {

    @Override
    public String getRequestUrl() {
        return ImgurApiConstants.URL_MESSAGE_GET_MESSAGES;
    }

    @Override
    protected Class<GetMessagesResponse> getResponseClass() {
        return GetMessagesResponse.class;
    }

    public class GetMessagesResponse extends Basic<List<Message>> {

    }
}
