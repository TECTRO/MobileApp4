package com.tectro.mobileapp4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.tectro.mobileapp4.Adapter.GameTableAdapter;
import com.tectro.mobileapp4.ConnectionModule.ConnectionManager;
import com.tectro.mobileapp4.ConnectionModule.IConnection;
import com.tectro.mobileapp4.GameModel.GameModel;
import com.tectro.mobileapp4.GameModel.additional.DrawHelper;
import com.tectro.mobileapp4.GameModel.additional.Figure;
import com.tectro.mobileapp4.GameModel.additional.Player;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity implements IConnection {

    GameModel gameModel;
    Animation itemAnimation;
    ConnectionManager connectionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        itemAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce_animation);
        gameModel = GameModel.CreateInstance(2);
        connectionManager = new ConnectionManager();
        connectionManager.TryAttach(this, R.id.GameTableFragment);
        UpdatePreviews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        UpdatePreviews();
    }

    private void UpdatePreviews() {

        Player current = gameModel.getPlayerManager().GetCurrent();
        DrawHelper drawer = gameModel.getDHelper();

        connectionManager.Update("UpdPlColor", current);

        drawer.ClearBitmap();
        if (current.getFigureToPlace() != null)
            drawer.DrawFigure(current.getFigureToPlace());
        ((ImageView) findViewById(R.id.CurrentCellHolder)).setImageBitmap(drawer.GetBitmap());

        drawer.ClearBitmap();
        if (current.getFigureToPlaceNext() != null)
            drawer.DrawFigure(current.getFigureToPlaceNext());
        ((ImageView) findViewById(R.id.NextCellHolder)).setImageBitmap(drawer.GetBitmap());

        UpdateLockBtn();
    }


    public void ShowListFragment(View view) {
        view.startAnimation(itemAnimation);
        Intent intent = new Intent(this, ActivityDetail.class);
        startActivity(intent);

    }

    public void nextRound(View view) {
        gameModel.NextRound();
        UpdatePreviews();
    }

    public void UpdateLockBtn() {
        if (gameModel.CanGoToNextRound())
            findViewById(R.id.NextRoundBtn).setVisibility(View.VISIBLE);
        else
            findViewById(R.id.NextRoundBtn).setVisibility(View.GONE);

    }

    public void StartAnimation(View view) {
        view.startAnimation(itemAnimation);
    }

    @Override
    public void Update(String Key, Object value) {
        if (Key.equals("next_figure")) {
            Figure f = gameModel.getRemainingFigure((Integer) value);
            gameModel.getPlayerManager().GetCurrent().setFigureToPlaceNext(f);

            UpdatePreviews();
        }

        if (Key.equals("checkNextRoundAccessibility")) {
            UpdateLockBtn();
        }
    }
}