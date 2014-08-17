package com.ezimgur.ui.message;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ezimgur.R;
import com.ezimgur.datacontract.Conversation;
import com.ezimgur.service.request.GetConversationRequest;
import com.ezimgur.ui.base.BaseFragment;
import com.ezimgur.ui.message.adapter.MessagesAdapter;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;


import butterknife.InjectView;

/**
 * Created by mharris on 8/16/14.
 * © 2014 NCR Corporation
 */
public class MessageDetailFragment extends BaseFragment {

    @InjectView(R.id.frag_msg_detail_tv_subject)TextView mTxtSubject;
    @InjectView(R.id.frag_msg_detail_progress)ProgressBar mProgressIndicator;
    @InjectView(R.id.frag_msg_detail_lv_thread)ListView mListThread;
    @InjectView(R.id.frag_msg_detail_et_reply)EditText mTextReply;
    @InjectView(R.id.frag_msg_details_btn_send)Button mBtnSend;
    @InjectView(R.id.frag_msg_detail_ib_delete)ImageButton mBtnDelete;

    public static MessageDetailFragment newInstance(int conversationId) {
        MessageDetailFragment detailFragment = new MessageDetailFragment();

        Bundle args = new Bundle();
        args.putInt("convoId", conversationId);

        detailFragment.setArguments(args);

        return detailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflate(inflater, container, R.layout.frag_message_detail);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int convoId = getArguments().getInt("convoId");

        loadMessageThread(convoId, 0);
    }

    private void loadMessageThread(int convoId, int page){
        mProgressIndicator.setVisibility(View.VISIBLE);

        activity().getRequestService().execute(new GetConversationRequest(convoId, page), conversationLoaded);

    }

    public void setConversation(Conversation conversation){
        mTxtSubject.setText("convo with " + conversation.withAccountUsername);
        mListThread.setAdapter(new MessagesAdapter(conversation.messages));
    }

    private void attachViewListeners(){
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replyOnThread();
            }
        });

        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteThread();
            }
        });
    }

    private void replyOnThread(){
        String reply = mTextReply.getText().toString();

        if (reply.length() > 0) {


        }
    }

    private void deleteThread(){

    }

    private RequestListener<Conversation> conversationLoaded = new RequestListener<Conversation>() {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            mProgressIndicator.setVisibility(View.GONE);
        }

        @Override
        public void onRequestSuccess(Conversation conversation) {
            mProgressIndicator.setVisibility(View.GONE);
            setConversation(conversation);
        }
    };
}
