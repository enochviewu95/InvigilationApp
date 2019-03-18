package com.example.avitor.invigilate;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class MainPageActivity extends AppCompatActivity {

    //Declaration of floating action menu
    private FloatingActionMenu floatingActionMenu;

    //Declaration of the framelayout
    private FrameLayout mMainFrameLayout;
    //Declaration of the floating action button
    private FloatingActionButton floatingActionHome;
    private FloatingActionButton floatingActionMessages;
    private FloatingActionButton floatingActionSettings;

    //Declaration of fragment
    private HomeFragment homeFragment;
    private MessagesFragment messagesFragment;
    private SettingsFragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);


        //Setting on click listeners for the floating action buttons
        floatingActionMenu = (FloatingActionMenu)findViewById(R.id.floatingActionMenu);
        floatingActionHome = (FloatingActionButton) findViewById(R.id.floatingActionHome);
        floatingActionMessages = (FloatingActionButton) findViewById(R.id.floatingActionMessages);
        floatingActionSettings = (FloatingActionButton) findViewById(R.id.floatingActionSettings);

        //Declaration of framelayout
        mMainFrameLayout = (FrameLayout)findViewById(R.id.mainFrameLayout);

        //initialization of fragment
        homeFragment = new HomeFragment();
        messagesFragment = new MessagesFragment();
        settingsFragment = new SettingsFragment();

        //Set default layout to home fragment
        setFragment(homeFragment);


        floatingActionHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(homeFragment);
            }
        });


        floatingActionMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(messagesFragment);
            }
        });

        floatingActionSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(settingsFragment);
            }
        });

    }


    //Replacing the
    public void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainFrameLayout,fragment);
        fragmentTransaction.commit();
    }
}
