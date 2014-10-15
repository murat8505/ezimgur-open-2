package com.ezimgur.service;

import android.app.Application;
import android.app.Notification;
import android.os.Build;

import com.octo.android.robospice.SpiceService;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.exception.CacheCreationException;

/**
 * Created by mharris on 8/14/14.
 *
 */
public class RequestService extends SpiceService {

    private static CacheManager cacheManager;
    private static final int REQUEST_THREAD_COUNT = 4;

    @Override
    public CacheManager createCacheManager(Application application) throws CacheCreationException {
        if (cacheManager == null)
            cacheManager = new CacheManager();
        return cacheManager;
    }

    @Override
    public int getThreadCount() {
        return REQUEST_THREAD_COUNT;
    }
}
