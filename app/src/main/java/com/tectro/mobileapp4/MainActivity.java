package com.tectro.mobileapp4;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.tectro.mobileapp4.Adapter.GameTableAdapter;
import com.tectro.mobileapp4.ConnectionModule.ConnectionManager;
import com.tectro.mobileapp4.ConnectionModule.IConnection;
import com.tectro.mobileapp4.GameModel.GameModel;
import com.tectro.mobileapp4.GameModel.additional.DrawHelper;
import com.tectro.mobileapp4.GameModel.additional.Figure;
import com.tectro.mobileapp4.GameModel.additional.Player;
import com.tectro.mobileapp4.GameModel.additional.Winner;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity implements IConnection {

    GameModel gameModel;
    Animation itemAnimation;
    ConnectionManager connectionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        gameModel = GameModel.CreateInstance(2);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowInsetsController c = getWindow().getInsetsController();
            if (c != null)
                c.hide(WindowInsets.Type.statusBars());
        } else
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        itemAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce_animation);
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
        drawer.DrawPlayerBound(current);

        if (current.getFigureToPlace() != null)
            drawer.DrawFigure(current.getFigureToPlace());
        ((ImageView) findViewById(R.id.CurrentCellHolder)).setImageBitmap(drawer.GetBitmap());

        drawer.ClearBitmap();
        drawer.DrawPlayerBound(gameModel.getPlayerManager().GetNext());
        if (current.getFigureToPlaceNext() != null) {
            drawer.DrawFigure(current.getFigureToPlaceNext());
        }
        ((ImageView) findViewById(R.id.NextCellHolder)).setImageBitmap(drawer.GetBitmap());

        UpdateLockBtn();
    }


    public void ShowListFragment(View view) {
        view.startAnimation(itemAnimation);
        Intent intent = new Intent(this, ActivityDetail.class);
        startActivity(intent);

    }

    public void UpdateLockBtn() {
        if (gameModel.CanGoToNextRound())
            findViewById(R.id.NextRoundView).setVisibility(View.VISIBLE);
        else
            findViewById(R.id.NextRoundView).setVisibility(View.GONE);

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

        if (Key.equals("ToNextRound")) {
            Winner winner = gameModel.GetWinner();
            if (winner == null) {
                gameModel.NextRound();
                UpdatePreviews();
            } else
                Toast.makeText(this, "Победил игрок " + (winner.getWinner().getIndex() + 1), Toast.LENGTH_LONG).show();

        }
    }
}