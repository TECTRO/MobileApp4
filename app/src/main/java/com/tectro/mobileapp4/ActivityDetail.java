package com.tectro.mobileapp4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;

import com.tectro.mobileapp4.ConnectionModule.IConnection;
import com.tectro.mobileapp4.GameModel.GameModel;
import com.tectro.mobileapp4.GameModel.additional.Figure;

public class ActivityDetail extends AppCompatActivity implements IConnection {

    private GameModel GModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            finish();
            return;
        }

        setContentView(R.layout.activity_detail);
        GModel = GameModel.GetInstance();
    }

    @Override
    public void Update(String Key, Object value) {
        if(Key.equals("next_figure"))
        {
            Figure f =  GModel.getRemainingFigure((Integer)value);
            GModel.getPlayerManager().GetCurrent().setFigureToPlaceNext(f);
            //GModel.SetFigureToNext(f);
            finish();
        }
    }
}