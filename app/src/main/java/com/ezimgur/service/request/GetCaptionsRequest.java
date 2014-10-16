package com.ezimgur.service.request;

import com.ezimgur.api.CommentApi;
import com.ezimgur.api.GalleryApi;

import javax.inject.Inject;

/**
 * Created by mharris on 10/15/14.
 *
 */
public class GetCaptionsRequest extends BaseRequest<CaptionsResponse> {

    @Inject
    protected GalleryApi api;
    private String itemId;

    public GetCaptionsRequest(String itemId) {
        super(CaptionsResponse.class);
        this.itemId = itemId;
    }

    @Override
    public CaptionsResponse loadDataFromNetwork() throws Exception {
        CaptionsResponse response = new CaptionsResponse();
        response.captions = api.getCommentsForGalleryItem(itemId);
        return response;
    }
}
