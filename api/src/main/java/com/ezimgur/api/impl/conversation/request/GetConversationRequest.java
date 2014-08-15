package com.ezimgur.api.impl.conversation.request;

import com.ezimgur.api.ImgurApiConstants;
import com.ezimgur.api.http.ApiGetRequest;
import com.ezimgur.datacontract.Basic;
import com.ezimgur.datacontract.Message;

import java.util.List;

/**
 * Created by mharris on 7/11/14.
 *
 */
public class GetConversationRequest extends ApiGetRequest<GetConversationRequest.GetConversationResponse> {

    private int id;

    public GetConversationRequest(int id) {
        this.id = id;
    }

    @Override
    public String getRequestUrl() {
        return String.format(ImgurApiConstants.URL_MESSAGE_GET_THREAD_BY_ID, this.id);
    }

    @Override
    protected Class<GetConversationResponse> getResponseClass() {
        return GetConversationResponse.class;
    }

    public class GetConversationResponse extends Basic<List<Message>> {

    }
}
