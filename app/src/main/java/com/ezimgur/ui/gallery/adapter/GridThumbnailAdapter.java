package com.ezimgur.ui.gallery.adapter;

import android.graphics.PorterDuff;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ezimgur.EzApplication;
import com.ezimgur.R;
import com.ezimgur.api.ImageApi;
import com.ezimgur.datacontract.GalleryAlbum;
import com.ezimgur.datacontract.GalleryItem;
import com.ezimgur.datacontract.Image;
import com.ezimgur.datacontract.ImageSize;
import com.ezimgur.ui.base.UiBuilder;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by mharris on 10/14/14.
 * © 2014 NCR Corporation
 */
public class GridThumbnailAdapter extends BaseAdapter {

    @Inject
    protected ImageApi imageApi;

    private List<GalleryItem> items;

    private static final String TAG = "ezimgur.gridthumbnailadapter";

    public GridThumbnailAdapter(List<GalleryItem> items) {
        this.items = items;

        EzApplication.app().inject(this);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public GalleryItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).id.hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = UiBuilder.inflate(parent.getContext(), R.layout.view_grid_thumbnail);

            holder = new ViewHolder();
            holder.ivThumb = (ImageView)convertView.findViewById(R.id.view_grid_thumb_iv);
            holder.tvTitle = (TextView)convertView.findViewById(R.id.view_grid_tv_title);

            holder.ivThumb.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            ImageView view = (ImageView) v;
                            //overlay is black with transparency of 0x77 (119)
                            view.getDrawable().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                            view.invalidate();
                            break;
                        }
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL: {
                            ImageView view = (ImageView) v;
                            //clear the overlay
                            view.getDrawable().clearColorFilter();
                            view.invalidate();
                            break;
                        }
                    }

                    return true;
                }
            });

            convertView.setTag(holder);
        }else {
            holder  = (ViewHolder) convertView.getTag();
        }

        GalleryItem item = items.get(position);

        boolean isAlbum = item instanceof GalleryAlbum;
        String targetHash = isAlbum ? ((GalleryAlbum)item).cover : item.id;

        Image image = new Image();
        image.id = targetHash;
        image.mimeType = "image/jpeg";

        final String imageUrl = imageApi.getHttpUrlForImage(image, ImageSize.BIG_SQUARE);

        Log.d(TAG, "loading thumbnail "+imageUrl);
        holder.ivThumb.setImageDrawable(null);
        holder.tvTitle.setText(item.title);

        ImageLoader loader = EzApplication.getImageLoader();
        loader.displayImage(imageUrl, holder.ivThumb);

        return convertView;
    }

    static class ViewHolder {
        ImageView ivThumb;
        TextView tvTitle;
    }
}
