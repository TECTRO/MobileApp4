package com.tectro.mobileapp4.GameModel.additional;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.tectro.mobileapp4.FigureModifiers.HeightEnum;
import com.tectro.mobileapp4.FigureModifiers.MarkEnum;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Function;

import static com.tectro.mobileapp4.FigureModifiers.ShapeEnum.Circle;
import static com.tectro.mobileapp4.FigureModifiers.ShapeEnum.Square;

public class DrawHelper {

    //region Accessors
    public int getBitmapSize() {
        return BitmapSize;
    }

    public Paint getPaint() {
        return paint;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public ArrayList<Integer> getPlayersBoundColors() {
        return PlayersBoundColors;
    }
    //endregion

    public DrawHelper(int bitmapSize, int playersAmount) {
        rand = new Random();
        //GetPlayerIndex = getPlayerIndex;
        GeneratePlayersBoundColors(playersAmount);
        BitmapSize = bitmapSize;
        this.bitmap = Bitmap.createBitmap(BitmapSize, BitmapSize, Bitmap.Config.RGB_565);
        this.canvas = new Canvas(bitmap);
        this.paint = new Paint();
    }

    //Function<Player, Integer> GetPlayerIndex;

    private int BitmapSize;

    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint;
    private Random rand;

    public void ClearBitmap() {
        bitmap.eraseColor(Color.LTGRAY);
        paint.setStyle(Paint.Style.STROKE);
    }

    private ArrayList<Integer> PlayersBoundColors;

    public Integer GetPlayerColor(Player player) {
        if (player != null)
            return PlayersBoundColors.get(player.getIndex());
        else return null;
    }

    private void GeneratePlayersBoundColors(int maxPlayerCount) {
        PlayersBoundColors = new ArrayList<>();

        for (int i = 0; i < maxPlayerCount; i++) {
            int color = 0;
            do {
                color = Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
            }
            while (PlayersBoundColors.contains(color));

            PlayersBoundColors.add(color);
        }

    }

    public void DrawPlayerBound(Player player) {
        int index = player.getIndex();

        if (index >= 0) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(BitmapSize / 23f);
            paint.setColor(PlayersBoundColors.get(index));
            canvas.drawRect(0, 0, BitmapSize, BitmapSize, paint);
        }
    }

    public void DrawPlayerMark(Player player) {
        int index = player.getIndex();
        ;

        if (index >= 0) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(PlayersBoundColors.get(index));

            float CircleRadius = BitmapSize / 5f / 2f;
            float bitmapAnchor = BitmapSize / 5f * 4f;

            canvas.drawCircle(bitmapAnchor, bitmapAnchor, CircleRadius, paint);
        }
    }

    public Bitmap GetBitmap() {
        return Bitmap.createBitmap(bitmap);
    }

    public void DrawFigure(Figure figure) {
        switch (figure.getColor()) {
            case Black: {
                paint.setColor(Color.BLACK);
            }
            break;
            case White: {
                paint.setColor(Color.WHITE);
            }
            break;
        }

        float width = (figure.getHeight() == HeightEnum.High) ?
                BitmapSize / 4f * 3 :
                BitmapSize / 2.4f;

        paint.setStrokeWidth(BitmapSize / 15f);

        switch (figure.getShape()) {
            case Circle: {
                canvas.drawCircle(BitmapSize / 2f, BitmapSize / 2f, width / 2f, paint);
            }
            break;
            case Square: {
                float margin = (BitmapSize - width) / 2f;
                canvas.drawRect(margin, margin, width + margin, width + margin, paint);
            }
            break;
        }

        if (figure.getMark() == MarkEnum.Exist) {
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(BitmapSize / 2f, BitmapSize / 2f, BitmapSize / 8f, paint);
        }

    }
}
