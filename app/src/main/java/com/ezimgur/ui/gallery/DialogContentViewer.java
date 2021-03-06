package com.ezimgur.ui.gallery;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ezimgur.R;


/**
 * Created by EggmanProjects.
 * User: matthewharris
 * Date: 11/24/12
 * Time: 3:07 PM
 */
public class DialogContentViewer extends DialogFragment {

    private String mImageUrl;
    private boolean mForceWeb;

    private static final String TAG = "EzImgur.DialogContentViewer";

    public static DialogContentViewer newInstance(String imageUrl, boolean forceInWeb) {
        DialogContentViewer dialogFragment = new DialogContentViewer();

        Bundle bundle = new Bundle();
        bundle.putString("image_url", imageUrl);
        bundle.putBoolean("force_web", forceInWeb);
        dialogFragment.setArguments(bundle);

        return dialogFragment;
    }

    public static DialogContentViewer newInstance(String imageUrl) {
        DialogContentViewer dialogFragment = new DialogContentViewer();

        Bundle bundle = new Bundle();
        bundle.putString("image_url", imageUrl);
        bundle.putBoolean("force_web", false);
        dialogFragment.setArguments(bundle);

        return dialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Theme_AppCompat);

        if (getArguments() != null) {
            mImageUrl = getArguments().getString("image_url");
            mForceWeb = getArguments().getBoolean("force_web");
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.frag_dialog_content_viewer, container, false);
        final WebView webView = (WebView) v.findViewById(R.id.twv_dlg_webview);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadUrl(mImageUrl);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
        });

        getDialog().setCanceledOnTouchOutside(true);

        return v;
    }
}