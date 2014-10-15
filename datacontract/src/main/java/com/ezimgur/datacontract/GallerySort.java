package com.ezimgur.datacontract;

/**
 * Created by mharris on 10/14/14.
 * © 2014 NCR Corporation
 */
public enum  GallerySort {
    POPULARITY,
    NEWEST_FIRST,
    HIGHEST_SCORING,
    RISING;

    public static String getValueForApi(GallerySort sort) {
        switch (sort){
            case POPULARITY:
                return "viral";
            case NEWEST_FIRST:
                return "time";
            case HIGHEST_SCORING:
                return "top";
            case RISING:
                return "rising";
        }
        return null;
    }
}
