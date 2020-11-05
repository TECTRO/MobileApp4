package com.tectro.mobileapp4.GameModel.additional;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.function.Supplier;

public class Player {
    //region Accessors
    private Supplier<List<Player>> listSupplier;
    public int getIndex() { return listSupplier.get().indexOf(this); }

    public Figure getFigureToPlace() {
        return FigureToPlace;
    }

    public void setFigureToPlace(Figure figureToPlace) {
        FigureToPlace = figureToPlace;
    }

    public Figure getFigureToPlaceNext() {
        return currentManager.GetNext().FigureToPlace;
    }

    public void setFigureToPlaceNext(Figure figureToPlaceNext) {
        currentManager.GetNext().FigureToPlace = figureToPlaceNext;
    }
    //endregion

    PlayerManager currentManager;

    public Player(@NonNull PlayerManager manager, Supplier<List<Player>> getPlayers) {
        currentManager = manager;
        listSupplier = getPlayers;
    }

    private Figure FigureToPlace;

    public Cell PutFigure() {
        if (FigureToPlace != null) {
            Cell newCell = new Cell(this, FigureToPlace);
            FigureToPlace = null;
            return newCell;
        }
        return null;
    }

    public Cell GetCell() {
        return new Cell(this, FigureToPlace);
    }
}