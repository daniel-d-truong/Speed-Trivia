package com.example.danie.testappusingjava;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import static android.support.constraint.Constraints.TAG;

public class BlankFragment extends Fragment {

    public static HashMap<Integer, HashMap<String, String>> hmap = new HashMap<Integer, HashMap<String, String>>(); //will be edited by other java code
    public static int count = 0; //will be edited by other java files

    public static BlankFragment newInstance() {
        BlankFragment fragment = new BlankFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_home, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.list_all_quiz_scores);
        if (listView == null){
            Log.d(TAG, "onCreateView listView IS NULL");
        }
        else
            Log.d(TAG, "onCreateView listView IS NOT NULL");
        BlankFragment.CustomAdapter customAdapter = new BlankFragment.CustomAdapter();
        listView.setAdapter(customAdapter);
        return rootView;
    }



    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return hmap.size();
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
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_score_list_view, null);
            }
            TextView scoreFraction = (TextView) convertView.findViewById(R.id.fraction);
            TextView scorePercent = (TextView) convertView.findViewById(R.id.percentTextView2);
            TextView categoryText = (TextView) convertView.findViewById(R.id.categoryText);
            TextView difficultyText = (TextView) convertView.findViewById(R.id.difficultyText);
            TextView numqText = (TextView) convertView.findViewById(R.id.num_questions_text);

            scoreFraction.setText(hmap.get(position).get("scoreFraction"));
            scorePercent.setText(hmap.get(position).get("scorePercent"));
            categoryText.setText(hmap.get(position).get("categoryText"));
            difficultyText.setText(hmap.get(position).get("difficultyText"));
            numqText.setText(hmap.get(position).get("numqText"));


            return convertView;
        }
    }
}

