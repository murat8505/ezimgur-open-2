package com.ezimgur.app;

import com.ezimgur.api.AccountApi;
import com.ezimgur.api.AlbumApi;
import com.ezimgur.api.AuthenticationApi;
import com.ezimgur.api.CommentApi;
import com.ezimgur.api.ConversationApi;
import com.ezimgur.api.GalleryApi;
import com.ezimgur.api.ImageApi;
import com.ezimgur.api.MessageApi;
import com.ezimgur.api.NotificationApi;
import com.ezimgur.api.impl.account.AccountApiImpl;
import com.ezimgur.api.impl.album.AlbumApiImpl;
import com.ezimgur.api.impl.authentication.AuthenticationApiImpl;
import com.ezimgur.api.impl.comment.CommentApiImpl;
import com.ezimgur.api.impl.conversation.ConversationApiImpl;
import com.ezimgur.api.impl.gallery.GalleryApiImpl;
import com.ezimgur.api.impl.image.ImageApiImpl;
import com.ezimgur.api.impl.message.MessageApiImpl;
import com.ezimgur.api.impl.notification.NotificationApiImpl;
import com.ezimgur.service.request.CreateMessageRequest;
import com.ezimgur.service.request.DeleteConversationRequest;
import com.ezimgur.service.request.GetConversationRequest;
import com.ezimgur.service.request.GetConversationsRequest;
import com.ezimgur.service.request.RefreshTokenRequest;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mharris on 8/14/14.
 * © 2014 NCR Corporation
 */
@Module(
        injects = {
                RefreshTokenRequest.class,
                GetConversationsRequest.class,
                GetConversationRequest.class,
                DeleteConversationRequest.class,
                CreateMessageRequest.class
        },
        complete = false, library = true)
public class ServiceModule {

    @Provides @Singleton
    @SuppressWarnings("unused")
    public AccountApi provideAccountApi() {
        return new AccountApiImpl();
    }

    @Provides @Singleton
    @SuppressWarnings("unused")
    public AlbumApi provideAlbumApi() {
        return new AlbumApiImpl();
    }

    @Provides @Singleton
    @SuppressWarnings("unused")
    public AuthenticationApi provideAuthenticationApi() {
        return new AuthenticationApiImpl();
    }

    @Provides @Singleton
    @SuppressWarnings("unused")
    public CommentApi provideCommentApi() {
        return new CommentApiImpl();
    }

    @Provides @Singleton
    @SuppressWarnings("unused")
    public ConversationApi provideConversationApi() {
        return new ConversationApiImpl();
    }

    @Provides @Singleton
    @SuppressWarnings("unused")
    public GalleryApi provideGalleryApi() {
        return new GalleryApiImpl();
    }

    @Provides @Singleton
    @SuppressWarnings("unused")
    public ImageApi provideImageApi() {
        return new ImageApiImpl();
    }

    @Provides @Singleton
    @SuppressWarnings("unused")
    public MessageApi provideMessageApi() {
        return new MessageApiImpl();
    }

    @Provides @Singleton
    @SuppressWarnings("unused")
    public NotificationApi provideNotificationApi() {
        return new NotificationApiImpl();
    }

}
