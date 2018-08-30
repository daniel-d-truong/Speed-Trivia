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
import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class FetchJSON extends AsyncTask<Void, Void, Void> {
    String data = "";
    ArrayList<String> questionsList = new ArrayList<String>();
    String singleParsed = "";
    String link = "https://opentdb.com/api.php?amount=1&type=multiple";

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Log.d(TAG, "Process working in background");
            URL url = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line != null){
                line = bufferedReader.readLine();
//                if (line != null && ! line.toLowerCase().contains("&")) {
                data = data + line;
////                }

            }

            JSONObject JA_temp = new JSONObject(data);

            JSONArray JA = new JSONArray(JA_temp.getJSONArray("results").toString()); //MUST USE .toSTRING() TO PARSE CORRECTLY
            Log.d(TAG, "The for loop is not running");
            for(int i = 0; i < JA.length(); i++){
                JSONObject JO = (JSONObject) JA.get(i);
                singleParsed = JO.getString("question");
                questionsList.add(singleParsed);
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

//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        Log.d(TAG, data);
        Trivia.question.setText(questionsList.get(0));
    }

    public ArrayList<String> getQuestionsList() {
        return questionsList;
    }

    public void setLink(String url){
        link = url;
    }



}
