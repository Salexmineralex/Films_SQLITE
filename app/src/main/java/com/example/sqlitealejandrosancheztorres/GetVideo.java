package com.example.sqlitealejandrosancheztorres;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetVideo extends AsyncTask<String,String,String> {


    private String JSONURL;

    YouTubePlayerView yt;

    private Activity a;

    public GetVideo(String JSONURL, Activity a)
    {
        this.JSONURL = JSONURL;

        this.a = a;
    }

    @Override
    protected String doInBackground(String... strings) {
        String current = "";



        URL url;

        HttpURLConnection httpurlconnection = null;

        try {
            url = new URL(JSONURL);

            httpurlconnection = (HttpURLConnection) url.openConnection();

            InputStream is = (InputStream) httpurlconnection.getInputStream();

            InputStreamReader isr = new InputStreamReader(is);



            int data = isr.read();

            while (data != -1)
            {
                current += (char)data;

                System.out.println("AAAAAAAAAAAAAAa");

                data = isr.read();
            }



            return current;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return current;

    }


    @Override
    protected void onPostExecute(String s)
    {
        super.onPostExecute(s);

        try {

            System.out.println("AAAAAAAAAAAAAAA"+JSONURL);

            JSONObject jsonobject = new JSONObject(s);

            JSONArray jsonarray = jsonobject.getJSONArray("results");



            JSONObject jsonobject1 = jsonarray.getJSONObject(0);

            String key = jsonobject1.getString("key");

            System.out.println(key);

            ImageView iv = a.findViewById(R.id.imageView2);

            iv.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse("https://www.youtube.com/watch?v="+key));
                    a.startActivity(intent);
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}