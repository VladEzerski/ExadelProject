package com.ezerski.vladislav.exadelproject.services;

import android.util.Log;
import android.widget.Toast;

import com.ezerski.vladislav.exadelproject.model.Post;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.ezerski.vladislav.exadelproject.application.ExApp.getAppContext;
import static com.ezerski.vladislav.exadelproject.constants.Constants.ERROR;

public class DataLoader {

    public String stringPostsReturner(String urlString) {
        String dataString = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();

            int responseCode = urlConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                dataString = readStream(urlConnection.getInputStream());
            } else {
                Toast.makeText(getAppContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Log.e(TAG, ERROR, e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return dataString;
    }

    public List<Post> postsReturner(String urlString) {
        return parseData(stringPostsReturner(urlString));
    }

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuilder response = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            Log.e(TAG, ERROR, e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, ERROR, e);
                }
            }
        }
        return response.toString();
    }

    public List<Post> parseData(String jsonString) {
        Type listType = new TypeToken<ArrayList<Post>>() {
        }.getType();
        return new Gson().fromJson(jsonString, listType);
    }
}