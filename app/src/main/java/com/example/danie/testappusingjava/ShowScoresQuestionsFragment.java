package com.example.danie.testappusingjava;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.net.Uri.*;
import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowScoresQuestionsFragment extends Fragment {


    public ShowScoresQuestionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_show_scores_questions, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.list_view); //selects list view to displa questions


        if (listView == null){
            Log.d(TAG, "onCreateView listView IS NULL");
        }
        else
            Log.d(TAG, "onCreateView listView IS NOT NULL");

        CustomAdapter customAdapter = new CustomAdapter(getActivity(), Trivia.questionList); //getActivity() represents the context, questionLIst is the array
        listView.setAdapter(customAdapter); //puts in values


        return rootView;
    }

    class CustomAdapter extends ArrayAdapter {

        public CustomAdapter(@NonNull Context context, ArrayList<String> questions) {
            super(context, 0, questions);
        }

        @Override
        public int getCount() {
            return Trivia.total;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_view, null);
            }

            TextView qView = (TextView) convertView.findViewById(R.id.show_question);
            Button googleButton = (Button) convertView.findViewById(R.id.googleSearchButton);

            String quAnTemp = "Question: " + Trivia.questionList.get(position) + "\n\nAnswer: " + Trivia.correctList.get(position); //represents all 3 question, answer, and user chose in one

            if (!Trivia.rightOrWring.get(position)){ //sets background color based on whether user chose the right answer or wrong answer
                quAnTemp+="\nYou Chose: " + Trivia.youChose.get(position);
                convertView.setBackgroundColor(getResources().getColor(R.color.red_wrong));
            }
            else{
                convertView.setBackgroundColor(getResources().getColor(R.color.green));
            }

            String tempQuestion = Trivia.questionList.get(position);
            tempQuestion.replaceAll(" ", "+");
            tempQuestion.replaceAll("&", "+");
            final String url = "http://www.google.com/search?q="+tempQuestion;
            Log.d(TAG, url);

            googleButton.setOnClickListener(new View.OnClickListener() { //event listener that takes user to google web broswer and googles the question
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                }
            });


            qView.setText(decode(quAnTemp));
//            aView.setText("Answer: " + Trivia.correctList.get(position));
            return convertView;
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

}
