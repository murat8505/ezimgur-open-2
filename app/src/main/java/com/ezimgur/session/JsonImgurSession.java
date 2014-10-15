package com.ezimgur.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ezimgur.datacontract.AuthenticationToken;
import com.ezimgur.datacontract.Conversation;
import com.ezimgur.datacontract.GalleryItem;
import com.ezimgur.serializer.GsonUtils;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by mharris on 8/14/14.
 *
 */
public class JsonImgurSession implements ImgurSession {

    private Context context;
    private SessionState sessionState;
    private boolean needsToSave;

    private static final String KEY_SESSION = "session_state";

    @Inject
    @SuppressWarnings("unused")
    public JsonImgurSession(Context context) {
        this.context = context;
        restoreStateFromPersisetence();
    }

    @Override
    public void onLifeCyclePause() {
        if (needsToSave) {
            saveStateToPersistence();
        }
    }

    @Override
    public boolean isAuthenticated() {
        return sessionState.token != null && sessionState.token.isAuthenticated();
    }

    @Override
    public void setAuthenticationToken(AuthenticationToken token) {
        sessionState.token = token;
        needsToSave = true;
    }

    @Override
    public boolean isAuthenticationExpired() {
        return sessionState.token != null && sessionState.token.isExpired();
    }

    @Override
    public String getAccountName() {
        return sessionState.token.accountUserName;
    }

    @Override
    public AuthenticationToken getAuthenticationToken() {
        return sessionState.token;
    }

    @Override
    public void logoutUser() {
        sessionState.token = null;
        needsToSave = true;
    }

    @Override
    public void setConversations(List<Conversation> conversations) {
        this.sessionState.conversations = conversations;
        needsToSave = true;
    }

    @Override
    public List<Conversation> getConversations() {
        return this.sessionState.conversations;
    }

    @Override
    public void setCurrentGalleryItems(List<GalleryItem> items) {
        this.sessionState.galleryItems = items;
        needsToSave = true;
    }

    @Override
    public List<GalleryItem> getCurrentGalleryItems() {
        return this.sessionState.galleryItems;
    }

    private void restoreStateFromPersisetence() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        String jsonSessionState = preferences.getString(KEY_SESSION, null);
        this.sessionState = GsonUtils.getGsonInstance().fromJson(jsonSessionState, SessionState.class);

        if (sessionState == null)
            sessionState = new SessionState();
    }

    private void saveStateToPersistence() {

        String jsonSessionState = GsonUtils.getGsonInstance().toJson(this.sessionState);

        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(KEY_SESSION, jsonSessionState);
        editor.apply();

        needsToSave = false;
    }
}
