package com.ezimgur.service.request;

import com.ezimgur.api.GalleryApi;
import com.ezimgur.datacontract.GallerySort;
import com.ezimgur.datacontract.GalleryType;

import javax.inject.Inject;

/**
 * Created by mharris on 10/14/14.
 * © 2014 NCR Corporation
 */
public class GetGalleryRequest extends BaseRequest<GalleryResponse> {

    @Inject
    protected GalleryApi galleryApi;

    private GalleryType type;
    private GallerySort sort;
    private int page;

    public GetGalleryRequest(GalleryType type, GallerySort sort, int page) {
        super(GalleryResponse.class);
        this.type = type;
        this.sort = sort;
        this.page = page;
    }

    @Override
    public GalleryResponse loadDataFromNetwork() throws Exception {

        GalleryResponse response = new GalleryResponse();
        response.items = galleryApi.getGalleryItems(type, sort, page);
        return response;
    }
}
