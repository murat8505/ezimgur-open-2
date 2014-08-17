package com.ezimgur;

import android.app.Application;
import android.util.Log;

import com.ezimgur.api.AuthenticationApi;
import com.ezimgur.api.exception.ApiException;
import com.ezimgur.app.ServiceModule;
import com.ezimgur.app.ViewModule;
import com.ezimgur.session.ImgurSession;

import dagger.ObjectGraph;

/**
 * Created by mharris on 8/14/14.
 * © 2014 NCR Corporation
 */
public class EzApplication extends Application{

    private static ObjectGraph objectGraph;
    private static EzApplication app;

    private static final String TAG = "EzImgur.EzApplication";

    public EzApplication() {
        super();
        app = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        setTokenIfPresentOnSession();
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
}
