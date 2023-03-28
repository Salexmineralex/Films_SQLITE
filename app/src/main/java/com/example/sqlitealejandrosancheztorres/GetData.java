package com.example.sqlitealejandrosancheztorres;

import android.app.Activity;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetData extends AsyncTask<String,String,String> {


    private String JSONURL;

    private Activity a;

    public GetData(String JSONURL, Activity a)
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

                System.out.println();

                String Jsonurl2 = "http://api.themoviedb.org/3/movie/"+jsonobject1.getString("id")+"/videos?api_key=6dc2fa7afbc83f5c84eb4cfc95068114";

                GetVideo gv = new GetVideo(Jsonurl2,a);

                gv.execute();



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}