package com.ezimgur.ui.gallery;

import android.app.ActionBar;
import android.gesture.Gesture;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.VideoView;

import com.ezimgur.EzApplication;
import com.ezimgur.R;
import com.ezimgur.api.ImageApi;
import com.ezimgur.datacontract.Comment;
import com.ezimgur.datacontract.GalleryAlbum;
import com.ezimgur.datacontract.GalleryGif;
import com.ezimgur.datacontract.GalleryImage;
import com.ezimgur.datacontract.GalleryItem;
import com.ezimgur.datacontract.Image;
import com.ezimgur.datacontract.ImageSize;
import com.ezimgur.service.request.CaptionsResponse;
import com.ezimgur.service.request.GetCaptionsRequest;
import com.ezimgur.session.ImgurSession;
import com.ezimgur.ui.base.BaseFragment;
import com.ezimgur.ui.gallery.adapter.CaptionAdapter;
import com.ezimgur.ui.widget.ParallaxListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;


import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * Created by mharris on 10/15/14.
 *
 */
public class GalleryItemFragment extends BaseFragment{

    @InjectView(R.id.frag_gallery_item_lv)
    protected ParallaxListView lvImage;

    @Inject
    protected ImgurSession session;
    @Inject
    protected ImageApi imageApi;

    private GalleryItem item;
    private ImageView ivImage;
    private VideoView vvMovie;

    private GalleryImage image;
    private GalleryAlbum album;
    private GalleryGif gif;
    private Image currentImage;

    private CaptionAdapter captionAdapter;

    private boolean isGif;
    private boolean isAlbum;

    private boolean shown;
    private boolean animationStarted;


    public static GalleryItemFragment newInstance(int itemIndex) {
        GalleryItemFragment fragment = new GalleryItemFragment();

        Bundle args = new Bundle();
        args.putInt("selected_item", itemIndex);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflate(inflater, container, R.layout.frag_gallery_item);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View imageHeader = View.inflate(getActivity(), R.layout.view_header_image, null);

        ivImage = (ImageView) imageHeader.findViewById(R.id.view_header_image_iv_image);
        vvMovie = (VideoView) imageHeader.findViewById(R.id.view_header_image_vv_movie);
        TextView tvTitle = (TextView) imageHeader.findViewById(R.id.view_header_image_title);

        captionAdapter = new CaptionAdapter(item, new ArrayList<Comment>(), getFragmentManager());
        lvImage.setAdapter(captionAdapter);
        lvImage.addParallaxedHeaderView(imageHeader);
        tvTitle.setHint(item.title);

        activity().getSupportActionBar().setNavigationMode(android.support.v7.app.ActionBar.NAVIGATION_MODE_STANDARD);

        transformGalleryItemToTarget();
        if (!isAlbum) {
            loadImageAndSetViewState(currentImage);
        }

        final GestureDetector.SimpleOnGestureListener tapDetector = new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {

                DialogContentViewer viewer =  DialogContentViewer.newInstance(imageApi.getHttpUrlForImage(currentImage, ImageSize.ACTUAL_SIZE));
                viewer.show(getFragmentManager(), "FULL-SIZE");
                return true;
            }
        };
        final GestureDetector detector = new GestureDetector(getActivity(), tapDetector);

        ivImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return detector.onTouchEvent(event);
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            activity().getSupportActionBar().setTitle(item.id);
            activity().setTitle(item.id);
            activity().getSupportActionBar().setDisplayShowHomeEnabled(true);
            shown = true;
            if (!animationStarted && vvMovie != null) {
                vvMovie.start();
                animationStarted = true;
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();

        if (args != null) {
            int targetIndex = args.getInt("selected_item");
            item = session.getCurrentGalleryItems().get(targetIndex);

            activity().getRequestService().execute(new GetCaptionsRequest(item.id), captionsLoaded);
        }
    }

    private void loadImageAndSetViewState(Image image) {


        if (isGif) {
            vvMovie.setVideoURI(Uri.parse(gif.movieUrl));
            vvMovie.setVisibility(View.VISIBLE);
            vvMovie.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                }
            });
            if (shown && !animationStarted) {
                vvMovie.start();
            }
        } else {
            try {
                String imageUrl = imageApi.getHttpUrlForImage(image, ImageSize.ACTUAL_SIZE);

                ivImage.setMinimumHeight(image.height);
                ivImage.setMinimumWidth(image.width);
                ImageLoader imageLoader = EzApplication.getImageLoader();
                imageLoader.displayImage(imageUrl, ivImage);
            } catch (OutOfMemoryError error) {
                Toast.makeText(getActivity(), "not enough memory to keep showing images...", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void transformGalleryItemToTarget(){

        isAlbum = item instanceof GalleryAlbum;
        isGif = item instanceof GalleryGif;

        if (isAlbum) {
            isAlbum = true;
            album = (GalleryAlbum) item;
        } else if (isGif) {
            isGif = true;
            gif = (GalleryGif) item;
        } else {
            isAlbum = false;
            image  = (GalleryImage) item;
            currentImage = image.toImage();
        }
    }

    private RequestListener<CaptionsResponse> captionsLoaded = new RequestListener<CaptionsResponse>() {
        @Override
        public void onRequestFailure(SpiceException spiceException) {

        }

        @Override
        public void onRequestSuccess(CaptionsResponse captionsResponse) {
            captionAdapter.setCaptions(item, captionsResponse.captions);
        }
    };
}
