package com.durocrete_punesales_newapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.durocrete_punesales_newapp.R;


public class Splashactivity extends AppCompatActivity implements Animation.AnimationListener {


    Animation zoom_in;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashactivity);


        logo = (ImageView) findViewById(R.id.imgLogo);
        zoom_in = AnimationUtils.loadAnimation(this, R.anim.zoon_in);
        zoom_in.setAnimationListener(this);

        logo.startAnimation(zoom_in);


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(Splashactivity.this, LoginActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, 5000);
    }


    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == zoom_in) {
            Intent i = new Intent(Splashactivity.this, LoginActivity.class);
                startActivity(i);

                // close this activity
                finish();
        }

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
