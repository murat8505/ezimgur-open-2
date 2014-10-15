package com.ezimgur.api.impl.gallery.request;

import com.ezimgur.api.ImgurApiConstants;
import com.ezimgur.api.http.ApiGetRequest;
import com.ezimgur.api.impl.gallery.response.ResponseContainer;
import com.ezimgur.datacontract.GallerySort;
import com.ezimgur.datacontract.GalleryType;

/**
 * Created by EggmanProjects.
 * User: matthewharris
 * Date: 12/14/12
 * Time: 10:00 PM
 */
public class LoadGalleryRequest extends ApiGetRequest<ResponseContainer.GalleryLoadContainer> {

    private GalleryType type;
    private GallerySort sort;
    private int page;

    public LoadGalleryRequest(GalleryType type, GallerySort sort, int page) {;
        this.type = type;
        this.sort = sort;
        this.page = page;
    }

    @Override
    public String getRequestUrl() {
       return String.format(ImgurApiConstants.URL_GALLERY_LOAD, type.toString().toLowerCase(), GallerySort.getValueForApi(sort), page);
    }

    @Override
    protected Class<ResponseContainer.GalleryLoadContainer> getResponseClass() {
        return ResponseContainer.GalleryLoadContainer.class;
    }
}
