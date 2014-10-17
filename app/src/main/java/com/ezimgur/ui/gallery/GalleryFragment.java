package com.ezimgur.ui.gallery;

import android.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

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

    @Inject
    protected ImgurSession session;

    private static final int MENU_REFRESH = 0;
    private static final String TAG = "EzImgur.GalleryFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflate(inflater, container, R.layout.frag_gallery);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, new String[]{"Most Viral", "User Submitted"});

        /** Defining Navigation listener */
        ActionBar.OnNavigationListener navigationListener = new ActionBar.OnNavigationListener() {

            @Override
            public boolean onNavigationItemSelected(int itemPosition, long itemId) {
                Toast.makeText(activity(), "You selected : ", Toast.LENGTH_SHORT).show();
                return false;
            }
        };

        activity().getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        activity().getActionBar().setListNavigationCallbacks(typeAdapter, navigationListener);

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

        getActivity().setTitle("");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        if (menu.size() == 0) {
            menu.add(0, MENU_REFRESH, Menu.CATEGORY_SYSTEM, "refresh")
                    .setIcon(android.R.drawable.ic_popup_sync)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == MENU_REFRESH) {
            loadGalleryFromNetwork();
            return true;
        }

        return super.onOptionsItemSelected(item);
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
