package com.ezimgur.api.impl.conversation.request;

import com.ezimgur.api.ImgurApiConstants;
import com.ezimgur.api.http.ApiGetRequest;
import com.ezimgur.datacontract.Basic;
import com.ezimgur.datacontract.Conversation;
import com.ezimgur.datacontract.Message;

import java.util.List;

/**
 * Created by mharris on 7/11/14.
 *
 */
public class GetConversationRequest extends ApiGetRequest<GetConversationRequest.GetConversationResponse> {

    private int id;
    private int page;

    public GetConversationRequest(int id, int page) {
        this.id = id;
        this.page = page;
    }

    @Override
    public String getRequestUrl() {
        return String.format(ImgurApiConstants.URL_CONVERSATION_GET, this.id, this.page, 0);
    }

    @Override
    protected Class<GetConversationResponse> getResponseClass() {
        return GetConversationResponse.class;
    }

    public class GetConversationResponse extends Basic<Conversation> {

    }
}
