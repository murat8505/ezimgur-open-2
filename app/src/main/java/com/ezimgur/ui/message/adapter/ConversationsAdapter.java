package com.ezimgur.ui.message.adapter;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ezimgur.R;
import com.ezimgur.datacontract.Conversation;
import com.ezimgur.ui.base.UiBuilder;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by mharris on 8/16/14.
 *
 */
public class ConversationsAdapter extends BaseAdapter {

    private List<Conversation> conversations;

    public ConversationsAdapter(List<Conversation> conversations) {
        this.conversations = conversations;
    }

    @Override
    public int getCount() {
        return conversations.size();
    }

    @Override
    public Conversation getItem(int position) {
        return conversations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return conversations.get(position).id;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        Context context = parent.getContext();
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = UiBuilder.inflate(context, R.layout.view_conversation);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        Conversation conversation = conversations.get(position);

        holder.txtTitle.setText("from " + conversation.withAccountUsername);
        CharSequence time = DateUtils.getRelativeTimeSpanString(conversation.datetime * 1000, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        holder.txtDate.setText(time);
        holder.txtBody.setText(conversation.lastMessagePreview);
        holder.txtSubject.setText(conversation.messageCount + " messages");

        return view;
    }

    public void updateDataSet(List<Conversation> conversations) {
        this.conversations = conversations;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @InjectView(R.id.v_conversation_tv_title)
        TextView txtTitle;
        @InjectView(R.id.v_conversation_tv_date)
        TextView txtDate;
        @InjectView(R.id.v_conversation_tv_subject)
        TextView txtSubject;
        @InjectView(R.id.v_conversation_tv_body)
        TextView txtBody;

        public ViewHolder(View v) {
            ButterKnife.inject(this, v);
        }
    }
}
