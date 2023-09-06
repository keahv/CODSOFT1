package com.example.myquotesapp;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchAdviceTask extends AsyncTask<Void, Void, String> {

    private static final String API_URL = "https://api.adviceslip.com/advice";

    private OnAdviceFetchedListener listener;

    public FetchAdviceTask(OnAdviceFetchedListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Void... voids) {
        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if (listener != null) {
            listener.onAdviceFetched(s);
        }
    }

    public interface OnAdviceFetchedListener {
        void onAdviceFetched(String adviceJson);
    }
}

