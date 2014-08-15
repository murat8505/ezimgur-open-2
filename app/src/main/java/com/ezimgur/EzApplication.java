package com.ezimgur;

import android.app.Application;

import com.ezimgur.app.ServiceModule;
import com.ezimgur.app.ViewModule;

import dagger.ObjectGraph;

/**
 * Created by mharris on 8/14/14.
 * © 2014 NCR Corporation
 */
public class EzApplication extends Application{

    private static ObjectGraph objectGraph;
    private static EzApplication app;

    public EzApplication() {
        super();
        app = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

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

    public static EzApplication app() {
        return app;
    }
}
