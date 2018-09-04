package com.example.danie.testappusingjava;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Objects;

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
        dynamicListView();


        return rootView;
    }

    public void dynamicListView(){
        LayoutInflater inflateCustom = (LayoutInflater) LayoutInflater.from(getContext());
        LinearLayout linearContainer = (LinearLayout) inflateCustom.inflate(R.layout.fragment_show_scores_questions, null);

        ListView listView = (ListView) linearContainer.findViewById(R.id.list_view);
        if (listView == null)
            Log.d(TAG, "listview is NULL");
        else
            Log.d(TAG, "listview is NOT NULL");
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
    }

    class CustomAdapter extends BaseAdapter {

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
            View view = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_view, null);

            TextView qView = (TextView) view.findViewById(R.id.show_question);
            TextView aView = (TextView) view.findViewById(R.id.show_answer);

            qView.setText(Trivia.questionList.get(position));
            aView.setText(Trivia.correctList.get(position));
            return view;
        }
    }

}
