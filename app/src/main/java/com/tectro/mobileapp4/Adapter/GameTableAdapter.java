package com.tectro.mobileapp4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tectro.mobileapp4.R;

import java.util.function.BiConsumer;

public class GameTableAdapter extends RecyclerView.Adapter<GameTableAdapter.ViewHolder>{

    private LayoutInflater inflater;
    private Object[][] items;
    private BiConsumer<Integer, Integer> consumer;

    public GameTableAdapter(Context context, Object[][] items, BiConsumer<Integer, Integer> consumer)
    {
        this.inflater = LayoutInflater.from(context);
        this.items = items;
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
        int x = position/items.length;
        int y = position%items.length;
        holder.CellView.setOnClickListener(r->consumer.accept(x,y));

        Object item = items[x][y];

        //do Something With View, show view Changes, specify them
        //example:
        holder.CellView.animate();

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView CellView;

        ViewHolder(View view) {
            super(view);
            CellView = view.findViewById(R.id.GameTableCellImage);
        }
    }
}
