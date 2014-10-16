package com.ezimgur.ui.gallery;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ezimgur.R;
import com.ezimgur.session.ImgurSession;
import com.ezimgur.ui.base.BaseFragment;
import com.ezimgur.ui.gallery.adapter.GalleryItemPagerAdapter;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * Created by mharris on 10/15/14.
 *
 */
public class GalleryItemPagerFragment extends BaseFragment {

    @InjectView(R.id.frag_gallery_pager_vp)
    protected ViewPager itemViewPager;

    @Inject
    protected ImgurSession session;

    private int currentIndex;

    public static GalleryItemPagerFragment newInstance(int targetItem) {
        GalleryItemPagerFragment fragment = new GalleryItemPagerFragment();

        Bundle args = new Bundle();
        args.putInt("current_item", targetItem);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflate(inflater, container, R.layout.frag_gallery_pager);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int totalCount = session.getCurrentGalleryItems().size();

        itemViewPager.setAdapter(new GalleryItemPagerAdapter(getFragmentManager(), totalCount));
        itemViewPager.setCurrentItem(currentIndex);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();

        if (args != null) {
            currentIndex = args.getInt("current_item");
        }
    }
}
