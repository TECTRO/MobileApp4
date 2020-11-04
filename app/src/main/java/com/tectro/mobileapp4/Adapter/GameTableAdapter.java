package com.tectro.mobileapp4.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tectro.mobileapp4.FigureModifiers.HeightEnum;
import com.tectro.mobileapp4.FigureModifiers.MarkEnum;
import com.tectro.mobileapp4.GameModel.GameModel;
import com.tectro.mobileapp4.GameModel.additional.Cell;
import com.tectro.mobileapp4.GameModel.additional.DrawHelper;
import com.tectro.mobileapp4.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class GameTableAdapter extends RecyclerView.Adapter<GameTableAdapter.ViewHolder> {

    private LayoutInflater inflater;
    //private Cell[][] items;
    private Function<Integer, Integer[]> consumer;
    private final Animation itemAnimation;
    // private DrawHelper drawHelper;
    //   int MatrixRank;

    GameModel GModel;

    public GameTableAdapter(Context context, Function<Integer, Integer[]> consumer) {//, Cell[][] items, DrawHelper drawHelper,
        GModel = GameModel.GetInstance();
        //   this.drawHelper = drawHelper;
        this.inflater = LayoutInflater.from(context);
        //   this.items = items;

        itemAnimation = AnimationUtils.loadAnimation(inflater.getContext(), R.anim.bounce_animation);
        //   MatrixRank = items.length;


        this.consumer = consumer;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.game_table_cell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //int y = position / GModel.MatrixSize;
        //int x = position % GModel.MatrixSize;

        Cell item = GModel.getTableFigure(position);

        DrawHelper drawHelper = GModel.getDHelper();

        drawHelper.ClearBitmap();
        if (item != null) {
            if (item.getFigure() != null)
                drawHelper.DrawFigure(item.getFigure());
            if (item.getOwner() != null)
                drawHelper.DrawPlayerMark(item.getOwner());
        }

        holder.CellView.setImageBitmap(drawHelper.GetBitmap());

        //if(!holder.CellView.hasOnClickListeners())
        holder.CellView.setOnClickListener(r ->
        {
            r.startAnimation(itemAnimation);
            Integer[] positions = consumer.apply(position);

            for (Integer pos : positions)
                this.notifyItemChanged(pos);
        });
    }

    @Override
    public int getItemCount() {
        return (int) Math.pow(GModel.MatrixSize, 2);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView CellView;

        ViewHolder(View view) {
            super(view);
            CellView = view.findViewById(R.id.GameTableCellImage);
        }
    }
}
