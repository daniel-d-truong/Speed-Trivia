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
import java.util.HashMap;

import static android.support.constraint.Constraints.TAG;

public class FetchJSON extends AsyncTask<Void, Void, Void> {
    String data = "";
    ArrayList<String> questionsList = new ArrayList<String>();
    ArrayList<String> answersList = new ArrayList<String>();
    ArrayList<String[]> incorrectList = new ArrayList<String[]>();

    HashMap<String, Integer> hmap = new HashMap<String, Integer>();

    String singleParsed = "";
    String link = "https://opentdb.com/api.php?amount=10&type=multiple";
    int originate; //0 = getting questions; 1 = getting list of categories

    public void setOriginate(int originate) {
        this.originate = originate;
    }

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

            JSONObject JA_temp = new JSONObject(data); //takes data and converts it into JSON

            if (originate == 0){
                JSONArray JA = new JSONArray(JA_temp.getJSONArray("results").toString()); //MUST USE .toSTRING() TO PARSE CORRECTLY
                Log.d(TAG, "The for loop is not running");
                for(int i = 0; i < JA.length(); i++){
                    JSONObject JO = (JSONObject) JA.get(i);
                    singleParsed = JO.getString("question");
                    questionsList.add(singleParsed);

                    answersList.add(JO.getString("correct_answer"));
                    JSONArray tempJson = (JSONArray) JO.get("incorrect_answers");
                    String[] temp = new String[3];
                    for (int k = 0; k < tempJson.length(); k++){
                        String x = (String) tempJson.get(k);
                        temp[k] = x; Log.d(TAG, "This tempJson works: "+x);
                    }
                    incorrectList.add(temp);

                    Log.d(TAG, "Parsed line" + singleParsed);
                }

            }

            else if (originate == 1){
                JSONArray JA = new JSONArray(JA_temp.getJSONArray("trivia_categories").toString());
                Log.d(TAG, "JSONArray " + JA);
                for (int i = 0; i < JA.length(); i++){
                    JSONObject JO = (JSONObject) JA.get(i);
                    hmap.put(JO.getString("name"), JO.getInt("id"));
                }

                if (hmap.get("General Knowledge") == null){
                    Log.d(TAG, "HMAP NOT WORKING");
                }
                else{
                    Log.d(TAG, "HMAP WORKS: " + hmap.get("General Knowledge"));
                }
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
        if (originate == 0){
            Trivia.question.setText(questionsList.get(0));
            Trivia.choice1.setText(answersList.get(0));
            Trivia.choice2.setText(incorrectList.get(0)[0]);
            Trivia.choice3.setText(incorrectList.get(0)[1]);
            Trivia.choice4.setText(incorrectList.get(0)[2]);
        }

    }

    public ArrayList<String> getQuestionsList() {
        return questionsList;
    }

    public ArrayList<String> getAnswersList() {
        return answersList;
    }

    public ArrayList<String[]> getIncorrectList() {
        return incorrectList;
    }

    public void setLink(String url){
        link = url;
    }

    public HashMap<String, Integer> getHmap() {
        return hmap;
    }



}
