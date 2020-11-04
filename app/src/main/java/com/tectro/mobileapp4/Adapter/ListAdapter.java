package com.tectro.mobileapp4.Adapter;

import android.content.Context;
import android.opengl.GLDebugHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tectro.mobileapp4.GameModel.GameModel;
import com.tectro.mobileapp4.GameModel.additional.DrawHelper;
import com.tectro.mobileapp4.R;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.zip.Inflater;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>  {

    private final LayoutInflater inflater;
    private final Animation itemAnimation;
    private final GameModel GModel;
    private Consumer<Integer> Func;

    public ListAdapter(Context context, Consumer<Integer> func ) {
        GModel = GameModel.GetInstance();

        this.inflater = LayoutInflater.from(context);
        Func = func;

        itemAnimation = AnimationUtils.loadAnimation(context, R.anim.bounce_animation);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.game_table_cell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DrawHelper helper = GModel.getDHelper();
        helper.ClearBitmap();
        helper.DrawFigure(GModel.getRemainingFigure(position));
        holder.CellView.setImageBitmap(helper.GetBitmap());
        holder.CellView.setOnClickListener(r->
        {
            r.startAnimation(itemAnimation);
            Func.accept(position);
        });
    }

    @Override
    public int getItemCount() {
        return GModel.getRemainingFiguresCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView CellView;

        ViewHolder(View view) {
            super(view);
            CellView = view.findViewById(R.id.GameTableCellImage);
        }
    }
}
