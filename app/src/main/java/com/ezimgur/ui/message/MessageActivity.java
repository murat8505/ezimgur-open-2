package com.ezimgur.ui.message;

import android.app.FragmentTransaction;
import android.os.Bundle;

import com.ezimgur.R;
import com.ezimgur.ui.base.BaseActivity;

/**
 * Created by mharris on 8/16/14.
 * © 2014 NCR Corporation
 */
public class MessageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.act_message_container, MessagesFragment.newInstance());
        transaction.commit();
    }

    @Override
    public int getContentViewId() {
        return R.layout.act_messages;
    }
}
