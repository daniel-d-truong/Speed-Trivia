package com.example.danie.testappusingjava;

import android.os.AsyncTask;
import android.os.Build;
import android.text.Html;
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
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import static android.support.constraint.Constraints.TAG;

public class FetchJSON extends AsyncTask<Void, Void, Void> {
    String data = ""; //this variable represents the json data in form of a string

    ///these arraylists will be used by trivia.class
    ArrayList<String> questionsList = new ArrayList<String>();
    ArrayList<String> answersList = new ArrayList<String>();
    ArrayList<String[]> incorrectList = new ArrayList<String[]>();

    HashMap<String, Integer> hmap = new HashMap<String, Integer>(); //used for categories

    String singleParsed = "";
    String link = "https://opentdb.com/api.php?amount=10&type=multiple";
    int originate; //0 = getting questions; 1 = getting list of categories

    public void setOriginate(int originate) {
        this.originate = originate;
    } //determines whether to get json questinos or json categories

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Log.d(TAG, "Process working in background");
            URL url = new URL(link); //link passed in by an activity

            //creates url connectino to fetch the json from the url
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line != null){
                line = (String) bufferedReader.readLine();
//                if (line != null){
//                    String lines = line.replaceAll("&quot", "\"").replaceAll("&#039", "\'");
//
//                    data+=lines;
//                }
//                else{
//                    data+=line;
//                }
//                if (line != null){
//
//                }
                data+=line;
            }

            JSONObject JA_temp = new JSONObject(data); //takes data and converts it into JSON

            if (originate == 0){ ///process used when fetching questions n choices
                JSONArray JA = new JSONArray(JA_temp.getJSONArray("results").toString()); //MUST USE .toSTRING() TO PARSE CORRECTLY
                Log.d(TAG, "The for loop is not running");
                for(int i = 0; i < JA.length(); i++){
                    JSONObject JO = (JSONObject) JA.get(i);
                    questionsList.add(JO.getString("question"));
                    answersList.add(JO.getString("correct_answer"));

                    //takes incorrect answers as an array
                    JSONArray tempJson = (JSONArray) JO.get("incorrect_answers");
                    String[] temp = new String[3];
                    for (int k = 0; k < tempJson.length(); k++){
                        String x = (String) tempJson.get(k);
                        temp[k] = x; Log.d(TAG, "This tempJson works: "+x);
                    }
                    incorrectList.add(temp);
                }

            }

            else if (originate == 1){ //deals with fetching categoreis
                JSONArray JA = new JSONArray(JA_temp.getJSONArray("trivia_categories").toString());
                Log.d(TAG, "JSONArray " + JA);
                for (int i = 0; i < JA.length(); i++){
                    JSONObject JO = (JSONObject) JA.get(i);
                    hmap.put(JO.getString("name"), JO.getInt("id")); //stores the name of the category with its id
                }

//                if (hmap.get("General Knowledge") == null){
//                    Log.d(TAG, "HMAP NOT WORKING");
//                }
//                else{
//                    Log.d(TAG, "HMAP WORKS: " + hmap.get("General Knowledge"));
//                }
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

        //sets initial question n answer
        if (originate == 0){
            Trivia.question.setText(decodeHTML(questionsList.get(0)));
            Trivia.choice1.setText(decodeHTML(answersList.get(0)));
            Trivia.choice2.setText(decodeHTML(incorrectList.get(0)[0]));
            Trivia.choice3.setText(decodeHTML(incorrectList.get(0)[1]));
            Trivia.choice4.setText(decodeHTML(incorrectList.get(0)[2]));
        }

    }

    private String decodeHTML(String line){
        if (Build.VERSION.SDK_INT >= 24)
        {
            return (Html.fromHtml(line , Html.FROM_HTML_MODE_LEGACY).toString());
        }
        else
        {
            return (Html.fromHtml(line).toString());
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
