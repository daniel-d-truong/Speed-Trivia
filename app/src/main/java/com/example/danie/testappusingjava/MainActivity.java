package com.example.danie.testappusingjava;

import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;
//import com.example.danie.parseJSON.parseJSON;

//https://code.tutsplus.com/tutorials/how-to-code-a-bottom-navigation-bar-for-an-android-app--cms-30305
//used to make bottom navigation
public class MainActivity extends AppCompatActivity {

    private ActionBar toolbar;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        parseJSON.parse();


        //CREATES bottom navigation view
        toolbar = getSupportActionBar();
        BottomNavigationView bottomNav = (BottomNavigationView) findViewById(R.id.navigationView);
        final BlankFragment homeFragment = new BlankFragment();
        final QuizFragment triviaFragment = new QuizFragment();
        final AccountFragment settingsFragment = new AccountFragment();

        if(savedInstanceState == null) { //if it is the first time loading the activity, set default fragment to HOME
            getSupportFragmentManager().
                    beginTransaction().replace(R.id.container,homeFragment).commit();
        }

//        transaction.replace(R.id.container, homeFragment);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) { //event listener for changing navigationmenu
                Log.d(TAG, menuItem.getTitle()+" has been selected.");
                Fragment selectedFragment = null;
                switch (menuItem.getItemId()){
                    case R.id.navigation_home:
                        selectedFragment = homeFragment;
                        break;
                    case R.id.navigation_quiz:
                        selectedFragment = triviaFragment;
                        break;
                    case R.id.navigation_account:
                        selectedFragment = settingsFragment;
                        break;

                }
                Log.d(TAG, "selectedFragment is set!");
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, selectedFragment);
                transaction.commit();
                return true;
            }

        });

    }







}


