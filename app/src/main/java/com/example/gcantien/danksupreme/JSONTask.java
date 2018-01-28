package com.example.gcantien.danksupreme;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by gcantien on 1/28/18.
 * JSONTask handles the networking in its own async thread. It attempts
 * to post JSONObject (passed in as a string as second arg) to the server
 * with inputted url and store a response in a text field.
 */

public class JSONTask extends AsyncTask<String, String, String> {

    private MainActivity activity;

    JSONTask(MainActivity main) {
        super();
        activity = main;
    }

    @Override
    protected String doInBackground(String... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            StringBuffer buffer;

            URL url = new URL(params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("POST");

            urlConnection.setDoOutput(true);

            urlConnection.connect();

            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.writeBytes("PostData=" + params[1]);
            wr.flush();
            wr.close();


            InputStream stream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            String line;
            buffer = new StringBuffer();

            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }


            return buffer.toString();

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        activity.urlTextView.setText(result);
    }
}
