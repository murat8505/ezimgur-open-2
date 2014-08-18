package com.ezimgur.ui.message.adapter;

import com.ezimgur.datacontract.Message;

import java.util.Comparator;

/**
 * Created by mharris on 8/17/14.
 * © 2014 NCR Corporation
 */
public class MessageComparator implements Comparator<Message> {

    @Override
    public int compare(Message lhs, Message rhs) {
        if (lhs.datetime > rhs.datetime)
            return -1;
        else if (lhs.datetime < rhs.datetime)
            return 1;
        else
            return 0;
    }
}
