package com.ezimgur.ui.message.adapter;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ezimgur.R;
import com.ezimgur.datacontract.Message;
import com.ezimgur.ui.base.UiBuilder;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by mharris on 8/16/14.
 * © 2014 NCR Corporation
 */
public class MessagesAdapter extends BaseAdapter {

    private List<Message> messages;

    public MessagesAdapter(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Message getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return messages.get(position).id;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        Context context = parent.getContext();
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = UiBuilder.inflate(context, R.layout.view_message_thread);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        Message message = messages.get(position);

        CharSequence time = DateUtils.getRelativeTimeSpanString(message.datetime * 1000, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        holder.txtDate.setText(time);
        holder.txtBody.setText(message.body);
        holder.txtFrom.setText(message.from);

        if ((position % 2) > 0)
            holder.threadContainer.setBackgroundResource(R.color.LightGrey);
        else
            holder.threadContainer.setBackgroundResource(android.R.color.transparent);

        return view;
    }

    static class ViewHolder {
        @InjectView(R.id.v_msg_thread_tv_body)
        TextView txtBody;
        @InjectView(R.id.v_msg_thread_rl_container)
        RelativeLayout threadContainer;
        @InjectView(R.id.v_msg_thread_tv_from)
        TextView txtFrom;
        @InjectView(R.id.v_msg_thread_tv_date)
        TextView txtDate;

        public ViewHolder(View v) {
            ButterKnife.inject(this, v);
        }
    }
}
