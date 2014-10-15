package com.ezimgur.datacontract;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mharris on 10/14/14.
 *
 */
public class GalleryGif extends GalleryImage {

    public boolean looping;
    @SerializedName("mp4")
    public String movieUrl;

    public GalleryGif(Parcel in) {
        super(in);
    }

    /*


account_url: "idrinkcheapbeer",
link: "http://i.imgur.com/l6yVgq1.jpg",
account_id: 5800783,
ups: 7086,
downs: 64,
score: 7151,
is_album: false
},
{


section: "gifs",
account_url: "steve699",
gifv: "http://i.imgur.com/QY2KKnz.gifv",
webm: "http://i.imgur.com/QY2KKnz.webm",
mp4: "http://i.imgur.com/QY2KKnz.mp4",
link: "http://i.imgur.com/QY2KKnz.gif",
looping: true,
account_id: 6079297,
ups: 4515,
downs: 52,
score: 5851,
is_album: false
},
     */
}
