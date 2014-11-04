package com.ezimgur.service.request;

import com.ezimgur.api.AlbumApi;

import javax.inject.Inject;

/**
 * Created by mharris on 11/3/14.
 *
 */
public class LoadAlbumImagesRequest extends BaseRequest<ImagesResponse> {

    @Inject
    protected AlbumApi albumApi;

    private String albumName;

    public LoadAlbumImagesRequest(String albumName) {
        super(ImagesResponse.class);

        this.albumName = albumName;
    }

    @Override
    public ImagesResponse loadDataFromNetwork() throws Exception {
        ImagesResponse response = new ImagesResponse();

        response.images = albumApi.getImagesForAlbum(albumName);

        return response;
    }
}
