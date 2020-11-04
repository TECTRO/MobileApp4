package com.tectro.mobileapp4.GameModel.additional;

import java.util.Objects;

public class Cell {
    //region Accessors
    public Player getOwner() {
        return Owner;
    }

    public Figure getFigure() {
        return Figure;
    }
    //endregion

    private Player Owner;
    private Figure Figure;

    public Cell(Player owner, Figure figure) {
        Owner = owner;
        Figure = figure;
    }

    //region Equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return Objects.equals(Owner, cell.Owner) &&
                Objects.equals(Figure, cell.Figure);
    }
    //endregion
}
