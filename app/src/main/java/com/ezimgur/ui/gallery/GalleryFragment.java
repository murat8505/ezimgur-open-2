package com.ezimgur.ui.gallery;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

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
    @InjectView(R.id.view_gallery_action_bar_sp_sort)
    protected Spinner spinSort;
    @InjectView(R.id.view_gallery_action_bar_sp_days)
    protected Spinner spinDays;
    @InjectView(R.id.frag_gallery_progress)
    protected SwipeRefreshLayout refreshLayout;

    @Inject
    protected ImgurSession session;

    private GalleryType currentType;
    private static final int MENU_REFRESH = 0;
    private static final String TAG = "EzImgur.GalleryFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflate(inflater, container, R.layout.frag_gallery);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, new String[]{"Most Viral", "User Submitted", "Reddit"});

        /** Defining Navigation listener */
        ActionBar.OnNavigationListener navigationListener = new ActionBar.OnNavigationListener() {

            @Override
            public boolean onNavigationItemSelected(int itemPosition, long itemId) {

                switch (itemPosition) {
                    case 0:
                        changeGallery(GalleryType.HOT);
                        return true;
                    case 1:
                        changeGallery(GalleryType.NEW);
                        return true;
                    case 2:
                        openCustomGalleryDialog();
                }

                return false;
            }
        };

        //refreshLayout.setDistanceToTriggerSync(0);
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem <= 0) {
                    refreshLayout.setEnabled(true);
                } else {
                    refreshLayout.setEnabled(false
                    );
                }
            }
        });
        refreshLayout.setColorSchemeColors(R.color.ezimgur_green_slight_transparent, R.color.ezimgur_red_slight_transparent);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadGalleryFromNetwork();
            }
        });

        activity().getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        activity().getSupportActionBar().setListNavigationCallbacks(typeAdapter, navigationListener);

        ArrayAdapter<String> sortAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, new String[]{"Popularity", "Newest First", "Highest Scoring"});
        spinSort.setAdapter(sortAdapter);

        ArrayAdapter<String> daysAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, new String[]{"Today", "1 Day Ago"});
        spinDays.setAdapter(daysAdapter);


        setHasOptionsMenu(true);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                activity().goToFragment(GalleryItemPagerFragment.newInstance(position));
            }
        });
        loadGalleryItemsFromCacheOrNetwork();
    }

    @Override
    public void onResume() {
        super.onResume();
        activity().getSupportActionBar().setTitle("");
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
        activity().getRequestService().execute(new GetGalleryRequest(currentType, GallerySort.POPULARITY, 0), galleryLoaded);
    }

    private void setItemsToGrid(List<GalleryItem> items) {
        gridView.setAdapter(new GridThumbnailAdapter(items));
    }

    private RequestListener<GalleryResponse> galleryLoaded = new RequestListener<GalleryResponse>() {

        @Override
        public void onRequestFailure(SpiceException e) {
            refreshLayout.setRefreshing(false);
        }

        @Override
        public void onRequestSuccess(GalleryResponse galleryResponse) {
            Log.d(TAG, "gallery loaded");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    refreshLayout.setRefreshing(false);
                }
            }, 1000);


            session.setCurrentGalleryItems(galleryResponse.items);
            setItemsToGrid(galleryResponse.items);
        }
    };

    private void changeGallery(GalleryType type) {
        currentType = type;
        loadGalleryFromNetwork();
    }

    private void openCustomGalleryDialog() {

    }
}
