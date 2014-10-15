package com.ezimgur.service.request;

import com.ezimgur.EzApplication;
import com.octo.android.robospice.request.SpiceRequest;

/**
 * Created by mharris on 8/14/14.
 *
 */
public abstract class BaseRequest<TResponseType> extends SpiceRequest<TResponseType> {

    public BaseRequest(Class<TResponseType> clazz) {
        super(clazz);

        EzApplication.app().inject(this);
    }

}
