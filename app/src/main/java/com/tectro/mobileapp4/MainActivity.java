package com.tectro.mobileapp4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void test(View v)
    {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.common_animation);
        findViewById(R.id.ButtonsBlock).startAnimation(animation);
    }
}