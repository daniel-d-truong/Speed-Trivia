package com.example.danie.testappusingjava;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.support.constraint.Constraints.TAG;

public class FetchJSON extends AsyncTask<Void, Void, Void> {
    String data = "";
    String dataParsed = "";
    String singleParsed = "";
    int index = 0;
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Log.d(TAG, "Process working in background");
            URL url = new URL("https://opentdb.com/api.php?amount=10&type=multiple");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }

            JSONArray JA_temp = new JSONArray(data);

            JSONArray JA = new JSONArray(JA_temp.getJSONArray(1));
            Log.d(TAG, "The for loop is not running");
            for(int i = 0; i < JA.length(); i++){
                JSONObject JO = (JSONObject) JA.get(i);
                singleParsed = "Question: " + JO.get("question");
                Log.d(TAG, "Parsed line" + singleParsed);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, data);
        Trivia.question.setText(this.singleParsed);
    }
}
