package com.ezimgur;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.ezimgur.api.AuthenticationApi;
import com.ezimgur.api.exception.ApiException;
import com.ezimgur.app.ServiceModule;
import com.ezimgur.app.ViewModule;
import com.ezimgur.session.ImgurSession;
import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.cache.disc.impl.ext.LruDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.io.IOException;

import dagger.ObjectGraph;

/**
 * Created by mharris on 8/14/14.
 *
 */
public class EzApplication extends Application{

    private static ObjectGraph objectGraph;
    private static EzApplication app;
    private static ImageLoader imageLoader;

    private static final String TAG = "EzImgur.EzApplication";

    public EzApplication() {
        super();
        app = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        setTokenIfPresentOnSession();

        initImageLoader(this);
    }

    public synchronized ObjectGraph getObjectGraph() {
        if (objectGraph == null){
            resetObjectGraph();
        }

        return objectGraph;
    }

    public void resetObjectGraph() {
        objectGraph = ObjectGraph.create(new ServiceModule(), new ViewModule());
    }

    public void inject(Object object) {
        getObjectGraph().inject(object);
    }

    private void setTokenIfPresentOnSession() {
        ImgurSession session = getObjectGraph().get(ImgurSession.class);

        if (session.isAuthenticated()) {
            AuthenticationApi authenticationApi = getObjectGraph().get(AuthenticationApi.class);
            try {
                authenticationApi.setCurrentAuthenticationToken(session.getAuthenticationToken());
            } catch (ApiException e) {
                Log.d(TAG, "unable to reset authentication token on app start");
            }
        }
    }

    public static EzApplication app() {
        return app;
    }

    private static void initImageLoader(Context context) {
        Log.d(TAG, "initImageLoaderStart");
        imageLoader = ImageLoader.getInstance();

        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(false)
                .build();

        ImageLoaderConfiguration config = null;
        try {
            config = new ImageLoaderConfiguration.Builder(context)
                    .threadPoolSize(5)
                    .threadPriority(Thread.NORM_PRIORITY - 1)
                    .diskCache(new LruDiscCache(context.getCacheDir(), new Md5FileNameGenerator(), 50 * 1024 * 1024))
                    .defaultDisplayImageOptions(displayImageOptions)
                    .build();
        } catch (IOException e) {
            Log.d(TAG, "setting up image loadeder configuration failed.");
        }
        imageLoader.init(config);

        Log.d(TAG, "initImageLoaderEnd");
    }

    public static ImageLoader getImageLoader(){
        return imageLoader;
    }
}
