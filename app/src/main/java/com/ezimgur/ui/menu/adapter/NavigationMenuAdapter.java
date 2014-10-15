package com.ezimgur.ui.menu.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ezimgur.R;
import com.ezimgur.ui.base.UiBuilder;
import com.ezimgur.ui.menu.NavigationMenuItem;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by mharris on 8/16/14.
 *
 */
public class NavigationMenuAdapter extends BaseAdapter {

    private List<NavigationMenuItem> menuItems;

    public NavigationMenuAdapter(List<NavigationMenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    @Override
    public int getCount() {
        return menuItems.size();
    }

    @Override
    public NavigationMenuItem getItem(int position) {
        return menuItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return menuItems.get(position).targetFragment.hashCode();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        Context context = parent.getContext();
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = UiBuilder.inflate(context, R.layout.view_nav_menu_item);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        NavigationMenuItem navigationMenuItem = menuItems.get(position);

        holder.txtTitle.setText(navigationMenuItem.title);

        return view;
    }

    static class ViewHolder {
        @InjectView(R.id.v_nav_menu_tv_title)
        TextView txtTitle;

        public ViewHolder(View v) {
            ButterKnife.inject(this, v);
        }
    }
}
