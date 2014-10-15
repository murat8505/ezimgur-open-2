package com.ezimgur.ui.message;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ezimgur.R;
import com.ezimgur.datacontract.Conversation;
import com.ezimgur.service.request.CreateMessageRequest;
import com.ezimgur.service.request.DeleteConversationRequest;
import com.ezimgur.service.request.GetConversationRequest;
import com.ezimgur.session.ImgurSession;
import com.ezimgur.ui.base.BaseFragment;
import com.ezimgur.ui.message.adapter.MessageComparator;
import com.ezimgur.ui.message.adapter.MessagesAdapter;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;


import java.util.Collections;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by mharris on 8/16/14.
 *
 */
public class MessageDetailFragment extends BaseFragment {

    @InjectView(R.id.frag_msg_detail_tv_subject)TextView mTxtSubject;
    @InjectView(R.id.frag_msg_detail_progress)ProgressBar mProgressIndicator;
    @InjectView(R.id.frag_msg_detail_lv_thread)ListView mListThread;
    @InjectView(R.id.frag_msg_detail_et_reply)EditText mTextReply;

    @Inject
    protected ImgurSession session;

    private int convoId;
    private String from;

    public static MessageDetailFragment newInstance(int conversationId, String from) {
        MessageDetailFragment detailFragment = new MessageDetailFragment();

        Bundle args = new Bundle();
        args.putInt("convoId", conversationId);
        args.putString("from", from);

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

        Bundle args = getArguments();
        convoId = args.getInt("convoId");
        from = args.getString("from");

        loadMessageThread(convoId, 0);
    }

    private void loadMessageThread(int convoId, int page){
        mProgressIndicator.setVisibility(View.VISIBLE);

        activity().getRequestService().execute(new GetConversationRequest(convoId, page), conversationLoaded);

    }

    public void setConversation(Conversation conversation){
        mTxtSubject.setText(getString(R.string.messages_convo_with) + conversation.withAccountUsername);
        mListThread.setAdapter(new MessagesAdapter(conversation.messages));
    }

    @OnClick(R.id.frag_msg_details_btn_send)
    protected void replyOnThread(){
        String reply = mTextReply.getText().toString();

        if (reply.length() > 0) {
            mProgressIndicator.setVisibility(View.VISIBLE);
            //invalidate old cache so messages are reloaded.
            session.setConversations(null);
            activity().getRequestService().execute(new CreateMessageRequest(from, reply), replySent);

        }
    }

    @OnClick(R.id.frag_msg_detail_ib_delete)
    protected void deleteThread(){
        activity().getRequestService().execute(new DeleteConversationRequest(convoId), conversationDeleted);
    }

    private RequestListener<Object> replySent = new RequestListener<Object>() {
        @Override
        public void onRequestFailure(SpiceException spiceException) {

        }

        @Override
        public void onRequestSuccess(Object o) {
            loadMessageThread(convoId, 0);
            mTextReply.setText("");
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mTextReply.getWindowToken(), 0);
        }

    };

    private RequestListener<Object> conversationDeleted = new RequestListener<Object>() {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(getActivity(), getString(R.string.messages_convo_unable_to_delete), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onRequestSuccess(Object o) {
            //invalidate the cache so messages are reloaded.
            session.setConversations(null);
            activity().getFragmentManager().popBackStack();
        }
    };

    private RequestListener<Conversation> conversationLoaded = new RequestListener<Conversation>() {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            mProgressIndicator.setVisibility(View.GONE);
        }

        @Override
        public void onRequestSuccess(Conversation conversation) {
            mProgressIndicator.setVisibility(View.GONE);

            Collections.sort(conversation.messages, new MessageComparator());
            setConversation(conversation);
        }
    };
}
