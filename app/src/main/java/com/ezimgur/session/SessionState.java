package com.ezimgur.session;

import com.ezimgur.datacontract.AuthenticationToken;
import com.ezimgur.datacontract.Conversation;
import com.ezimgur.datacontract.GalleryItem;

import java.util.List;

/**
 * Created by mharris on 8/14/14.
 *
 */
public class SessionState {

    public AuthenticationToken token;

    public List<Conversation> conversations;

    public List<GalleryItem> galleryItems;
}
