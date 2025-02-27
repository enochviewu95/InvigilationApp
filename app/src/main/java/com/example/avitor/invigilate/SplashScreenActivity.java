package com.example.avitor.invigilate;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class SplashScreenActivity extends AppCompatActivity {
    private ImageView logo;
    private ProgressBar mProgressBar;
    private boolean animationStarted = false;
    private final int splashTimeOut = 5000;
    private static boolean splashLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!splashLoaded) {
            getSupportActionBar().hide();
            setContentView(R.layout.activity_splash_screen);

            //logo of app
            logo = findViewById(R.id.invigilateIcon);
            //Starting animation
            Animation splashScreenAnimation = AnimationUtils.loadAnimation(this,
                    R.anim.splashscreenanim);
            logo.startAnimation(splashScreenAnimation);


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreenActivity.this, MainPageActivity.class);
                    startActivity(intent);
                }
            }, splashTimeOut);

            splashLoaded = true;
        }else{
            Intent goToMainActivity = new Intent(SplashScreenActivity.this,MainPageActivity.class);
            goToMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(goToMainActivity);
            finish();
        }

    }

}
