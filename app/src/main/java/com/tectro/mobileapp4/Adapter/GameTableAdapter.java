package com.tectro.mobileapp4.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tectro.mobileapp4.FigureModifiers.HeightEnum;
import com.tectro.mobileapp4.FigureModifiers.MarkEnum;
import com.tectro.mobileapp4.GameModel.GameModel;
import com.tectro.mobileapp4.GameModel.additional.DrawHelper;
import com.tectro.mobileapp4.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.function.BiConsumer;

public class GameTableAdapter extends RecyclerView.Adapter<GameTableAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<GameModel.FigureOwner> items;
    private BiConsumer<Integer, Integer> consumer;

    private DrawHelper drawHelper;
    int MatrixRank;

    public GameTableAdapter(Context context, GameModel.FigureOwner[][] items, DrawHelper drawHelper, BiConsumer<Integer, Integer> consumer) {

        this.drawHelper = drawHelper;
        this.inflater = LayoutInflater.from(context);
        this.items = new ArrayList<GameModel.FigureOwner>();

        MatrixRank = items.length;
        for (GameModel.FigureOwner[] item : items) this.items.addAll(Arrays.asList(item));

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
        int x = position / MatrixRank;
        int y = position % MatrixRank;
        holder.CellView.setOnClickListener(r -> consumer.accept(x, y));

        GameModel.FigureOwner item = items.get(position);

        drawHelper.ClearBitmap();
        if (item != null) {
            drawHelper.DrawPlayerBound(item.getPlayer()-1);
            drawHelper.DrawFigure(item.getFigure());
        }
        holder.CellView.setImageBitmap(drawHelper.GetBitmap());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView CellView;

        ViewHolder(View view) {
            super(view);
            CellView = view.findViewById(R.id.GameTableCellImage);
        }
    }
}
