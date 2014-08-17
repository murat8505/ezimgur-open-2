package com.ezimgur.app;

import android.content.Context;

import com.ezimgur.EzApplication;
import com.ezimgur.api.AuthenticationApi;
import com.ezimgur.session.ImgurSession;
import com.ezimgur.session.JsonImgurSession;
import com.ezimgur.ui.gallery.GalleryActivity;
import com.ezimgur.ui.login.LoginActivity;
import com.ezimgur.ui.menu.MenuFragment;
import com.ezimgur.ui.message.MessageActivity;
import com.ezimgur.ui.message.MessageDetailFragment;
import com.ezimgur.ui.message.MessagesFragment;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mharris on 8/14/14.
 * © 2014 NCR Corporation
 */
@Module (
        injects = {
                GalleryActivity.class,
                LoginActivity.class,
                MenuFragment.class,
                MessageActivity.class,
                MessagesFragment.class,
                MessageDetailFragment.class,

                //used in EzApplication
                ImgurSession.class,
                AuthenticationApi.class,

        },
        complete = false, library = true)
public class ViewModule {

    @Provides @Singleton
    @SuppressWarnings("unused")
    public Bus provideBus() {
        return new Bus();
    }

    @Provides @Singleton
    @SuppressWarnings("unused")
    public ImgurSession provideSession(Context context) {
        return new JsonImgurSession(context);
    }

    @Provides @Singleton
    @SuppressWarnings("unused")
    public Context provideContext() {
        return EzApplication.app();
    }
}
