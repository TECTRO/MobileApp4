package com.tectro.mobileapp4.GameModel.additional;

public class Winner {
    //region Constructor
    public Winner(Player winner, SuitableLine cells) {
        this.winner = winner;
        this.cells = cells;
    }
    //endregion

    //region Accessors
    public Player getWinner() {
        return winner;
    }

    public SuitableLine getCells() {
        return cells;
    }
    //endregion

    private final Player winner;
    private final SuitableLine cells;
}
