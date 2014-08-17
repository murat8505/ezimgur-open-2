package com.ezimgur.ui.message;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ezimgur.R;
import com.ezimgur.datacontract.Conversation;
import com.ezimgur.service.request.GetConversationsRequest;
import com.ezimgur.session.ImgurSession;
import com.ezimgur.ui.base.BaseFragment;
import com.ezimgur.ui.message.adapter.ConversationsAdapter;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * Created by mharris on 8/16/14.
 * © 2014 NCR Corporation
 */
public class MessagesFragment extends BaseFragment {

    @InjectView(R.id.frag_msg_progress)ProgressBar mProgressIndicator;
    @InjectView(R.id.frag_msg_txt_status)TextView mTxtStatus;
    @InjectView(R.id.frag_msg_lv_messages)ListView mListMessages;

    private ConversationsAdapter conversationsAdapter;

    @Inject
    protected ImgurSession session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflate(inflater, container, R.layout.frag_messages);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (session.isAuthenticated()){
            mTxtStatus.setText("No Messages");
            loadMessages();
        } else
            mTxtStatus.setText("Login from menu to see messages");

        getActivity().setTitle("messages");
    }

    private void loadMessages() {
        mProgressIndicator.setVisibility(View.VISIBLE);
        mTxtStatus.setVisibility(View.GONE);

        activity().getRequestService().execute(new GetConversationsRequest(), new RequestListener<GetConversationsRequest.Conversations>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {
                mTxtStatus.setVisibility(View.VISIBLE);
                mTxtStatus.setText("Unable to load messages. Imgur may be unreachable or down.");
                mProgressIndicator.setVisibility(View.GONE);
            }

            @Override
            public void onRequestSuccess(GetConversationsRequest.Conversations conversations) {
                setConversations(conversations);
                mProgressIndicator.setVisibility(View.GONE);
            }
        });
    }

    public void setConversations(List<Conversation> conversations){
        if (conversations != null && conversations.size() > 0){
            mTxtStatus.setVisibility(View.GONE);

            if (conversationsAdapter == null) {
                conversationsAdapter = new ConversationsAdapter(conversations);
                mListMessages.setAdapter(conversationsAdapter);
            } else {
                conversationsAdapter.updateDataSet(conversations);
            }

            attachViewListener();
        } else {
            mTxtStatus.setVisibility(View.VISIBLE);
            mTxtStatus.setText("You have no messages.");
        }
    }

    private void attachViewListener(){
        mListMessages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //mEventManager.fire(new OpenMessageDetailEvent(mMessagesAdapter.getItem(i).parentId));
            }
        });
    }

    public static MessagesFragment newInstance() {
        return new MessagesFragment();
    }
}
