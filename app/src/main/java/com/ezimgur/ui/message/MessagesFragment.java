package com.ezimgur.ui.message;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
 *
 */
public class MessagesFragment extends BaseFragment {

    @InjectView(R.id.frag_msg_progress)ProgressBar mProgressIndicator;
    @InjectView(R.id.frag_msg_txt_status)TextView mTxtStatus;
    @InjectView(R.id.frag_msg_lv_messages)ListView mListMessages;

    private ConversationsAdapter conversationsAdapter;

    private static final String MENU_REFRESH = "refresh";

    @Inject
    protected ImgurSession session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        conversationsAdapter = null;
        setHasOptionsMenu(true);
        return inflate(inflater, container, R.layout.frag_messages);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (session.isAuthenticated()){
            mTxtStatus.setText(getString(R.string.messages_you_have_none));
            loadMessages(false);
        } else
            mTxtStatus.setText(getString(R.string.messages_you_must_login));

    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setTitle(getString(R.string.messages_fragment_title));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.add(MENU_REFRESH)
                .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS)
                .setIcon(android.R.drawable.ic_popup_sync);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals(MENU_REFRESH)) {
            loadMessages(true);
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadMessages(boolean forceRefresh) {
        mProgressIndicator.setVisibility(View.VISIBLE);
        mTxtStatus.setVisibility(View.GONE);

        //load from network if no conversations cached, otherwise use cache until user refreshes manually.
        List<Conversation> cachedConversations = session.getConversations();
        if (cachedConversations == null || forceRefresh) {
            activity().getRequestService().execute(new GetConversationsRequest(), conversationsLoaded);
        } else {
            setConversations(cachedConversations);
            mProgressIndicator.setVisibility(View.GONE);
        }
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
            mTxtStatus.setText(getString(R.string.messages_you_have_none));
        }
    }

    private void attachViewListener(){
        mListMessages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Conversation convo = conversationsAdapter.getItem(i);
                activity().goToChildFragment(R.id.act_main_container, MessageDetailFragment.newInstance(convo.id, convo.withAccountUsername));
            }
        });
    }

    private RequestListener<GetConversationsRequest.Conversations> conversationsLoaded =
            new RequestListener<GetConversationsRequest.Conversations>() {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            mTxtStatus.setVisibility(View.VISIBLE);
            mTxtStatus.setText(getString(R.string.default_request_failure));
            mProgressIndicator.setVisibility(View.GONE);
        }

        @Override
        public void onRequestSuccess(GetConversationsRequest.Conversations conversations) {
            setConversations(conversations);
            mProgressIndicator.setVisibility(View.GONE);
        }
    };

    public static MessagesFragment newInstance() {
        return new MessagesFragment();
    }
}
