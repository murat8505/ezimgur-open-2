package com.ezimgur.serializer;

import com.ezimgur.datacontract.GalleryAlbum;
import com.ezimgur.datacontract.GalleryGif;
import com.ezimgur.datacontract.GalleryImage;
import com.ezimgur.datacontract.GalleryItem;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by mharris on 10/15/14.
 *
 */
public class GalleryItemSerializer implements JsonSerializer<GalleryItem> {

    @Override
    public JsonElement serialize(GalleryItem src, Type typeOfSrc, JsonSerializationContext context) {
        JsonParser parser = new JsonParser();

        String json = "";
        if (src instanceof GalleryGif) {
            json = GsonUtils.getGsonInstance().toJson(src, GalleryGif.class);
        } else if (src instanceof GalleryImage) {
            json = GsonUtils.getGsonInstance().toJson(src, GalleryImage.class);
        } else if (src instanceof GalleryAlbum) {
            json = GsonUtils.getGsonInstance().toJson(src, GalleryAlbum.class);
        }

        return  parser.parse(json);

    }
}
