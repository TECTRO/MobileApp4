package com.tectro.mobileapp4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.tectro.mobileapp4.Adapter.GameTableAdapter;
import com.tectro.mobileapp4.ConnectionModule.ConnectionManager;
import com.tectro.mobileapp4.ConnectionModule.IConnection;
import com.tectro.mobileapp4.GameModel.GameModel;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity {

    GameModel gameModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameModel = GameModel.CreateInstance(2);

    }

    public void test(View view) {
    }

    public void ShowListFragment(View view) {
    }

    // public void test(View v)
   // {
   //     Animation animation = AnimationUtils.loadAnimation(this, R.anim.common_animation);
   //     findViewById(R.id.ButtonsBlock).startAnimation(animation);
   // }

    //@Override
    //public void accept(ConnectionManager connectionManager, Integer integer) {
    //    connectionManager.Register(
    //            (IConnection) getSupportFragmentManager()
    //                    .findFragmentById(R.id.GameTableFragment)
    //    );
    //}
}