package com.example.ankwinam.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by axx42 on 2016-11-27.
 */

public class Community_item {
    public Bitmap image ;
    String title;
    RecyclerView wat;
    String imgUrl;
    private ImageLoadTask loadTask;

    Bitmap getImage(){
        return this.image;
    }
    String getTitle(){
        return this.title;
    }

    Community_item(String imageUrl, String title){
        this.imgUrl=imageUrl;
        this.title=title;
        this.image = null;
        loadTask = new ImageLoadTask();
    }

    public void loadImage(RecyclerView wat) {
        // HOLD A REFERENCE TO THE ADAPTER
        this.wat = wat;
        Log.e("Check_item",this.imgUrl);
        if (imgUrl != null && !imgUrl.equals("")) {
            loadTask.execute(this.imgUrl);
        }
    }
    private class ImageLoadTask extends AsyncTask<String, String, Bitmap> {

        @Override
        protected void onPreExecute() {
            Log.e("ImageLoadTask", "Loading image...");
        }

        // PARAM[0] IS IMG URL
        @Override
        protected Bitmap doInBackground(String... param) {
            Log.i("ImageLoadTask", "Attempting to load image URL: " + param[0]);
            try {
                Bitmap b = Walk_Info.getBitmapFromURL(param[0]);
                return b;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap ret) {
            if (ret != null) {
                Log.e("ImageLoadTask", "Successfully loaded " + imgUrl + " image");
                image = ret;
                if (wat != null) {
                    // WHEN IMAGE IS LOADED NOTIFY THE ADAPTER
                    wat.getAdapter().notifyDataSetChanged();
                }
            } else {
                Log.e("ImageLoadTask", "Failed to load " + imgUrl + " image");
            }
        }
    }
}