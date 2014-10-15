package com.ezimgur.ui.gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.ezimgur.R;
import com.ezimgur.datacontract.GalleryItem;
import com.ezimgur.datacontract.GallerySort;
import com.ezimgur.datacontract.GalleryType;
import com.ezimgur.service.request.GalleryResponse;
import com.ezimgur.service.request.GetGalleryRequest;
import com.ezimgur.session.ImgurSession;
import com.ezimgur.ui.base.BaseFragment;
import com.ezimgur.ui.gallery.adapter.GridThumbnailAdapter;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * Created by mharris on 10/14/14.
 *
 */
public class GalleryFragment extends BaseFragment {

    @InjectView(R.id.frag_gallery_grid)
    protected GridView gridView;

    @Inject
    protected ImgurSession session;

    private static final String TAG = "EzImgur.GalleryFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflate(inflater, container, R.layout.frag_gallery);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadGalleryItemsFromCacheOrNetwork();
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setTitle("gallery");
    }

    private void loadGalleryItemsFromCacheOrNetwork() {
        List<GalleryItem> galleryItems = session.getCurrentGalleryItems();

        if (galleryItems == null) {
            loadGalleryFromNetwork();

        } else {
            //we have cached items, use it instead.
            setItemsToGrid(galleryItems);
        }
    }

    private void loadGalleryFromNetwork() {
        activity().getRequestService().execute(new GetGalleryRequest(GalleryType.HOT, GallerySort.POPULARITY, 0), galleryLoaded);
    }

    private void setItemsToGrid(List<GalleryItem> items) {
        gridView.setAdapter(new GridThumbnailAdapter(items));
    }

    private RequestListener<GalleryResponse> galleryLoaded = new RequestListener<GalleryResponse>() {

        @Override
        public void onRequestFailure(SpiceException e) {

        }

        @Override
        public void onRequestSuccess(GalleryResponse galleryResponse) {
            Log.d(TAG, "gallery loaded");
            session.setCurrentGalleryItems(galleryResponse.items);
            setItemsToGrid(galleryResponse.items);
        }
    };
}
