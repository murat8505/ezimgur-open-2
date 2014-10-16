package com.ezimgur.ui.utils;

import android.text.style.URLSpan;

/**
 * Created by EggmanProjects.
 * User: matthewharris
 * Date: 11/24/12
 * Time: 2:13 PM
 */
public class URLSpanConverter implements RichTextUtils.SpanConverter<URLSpan, URLClickableSpan> {

    @Override
    public URLClickableSpan convert(URLSpan span) {
        return new URLClickableSpan(span.getURL());
    }
}