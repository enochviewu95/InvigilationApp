package com.example.avitor.invigilate;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainPageActivity extends AppCompatActivity {

    //Declaration of bottom navigation
    private BottomNavigationView mMainNav;

    //Declaration of the frameLayout
    private FrameLayout mMainFrameLayout;

    //Declaration of fragment
    private HomeFragment homeFragment;
    private MessagesFragment messagesFragment;
    private SettingsFragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        getSupportActionBar().hide();

        //Initialization of bottom navigation
        mMainNav = findViewById(R.id.bottomNav);

        //Declaration of frameLayout
        mMainFrameLayout = findViewById(R.id.mainFrameLayout);

        //Initialize the fragments
        homeFragment = new HomeFragment();
        settingsFragment = new SettingsFragment();
        messagesFragment = new MessagesFragment();

        //Set fragment to load home by default
        setFragment(homeFragment);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_nav:
                        setFragment(homeFragment);
                        return true;

                    case R.id.message_nav:
                        setFragment(messagesFragment);
                        return true;

                    case R.id.settings_nav:
                        setFragment(settingsFragment);
                        return true;

                        default:
                            return false;
                }
            }
        });

    }



    //Populating the currently set fragment
    public void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainFrameLayout,fragment);
        fragmentTransaction.commit();
    }



}
