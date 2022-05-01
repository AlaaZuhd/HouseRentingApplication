package com.example.lab_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    ImageView welcome_icon_imageview;
    TextView welcome_textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        welcome_icon_imageview = (ImageView) findViewById(R.id.welcome_icon_imageView);
        welcome_textview = (TextView) findViewById(R.id.welcome_textview);

        Animation animation1 = AnimationUtils.loadAnimation(WelcomeActivity.this,R.anim.welcome_icon_animation);
        Animation animation2 = AnimationUtils.loadAnimation(WelcomeActivity.this,R.anim.welcome_textview_animation);

        welcome_icon_imageview.setAnimation(animation1);
        welcome_textview.setAnimation(animation2);

        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(WelcomeActivity.this).toBundle());
                //finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animation2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(WelcomeActivity.this).toBundle());
                //finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


//        welcome_icon_imageview.startAnimation(AnimationUtils.loadAnimation(WelcomeActivity.this,R.anim.welcome_icon_animation));
//        welcome_textview.startAnimation(AnimationUtils.loadAnimation(WelcomeActivity.this,R.anim.welcome_textview_animation));

    }
}