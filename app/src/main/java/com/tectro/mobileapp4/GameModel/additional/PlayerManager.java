package com.tectro.mobileapp4.GameModel.additional;

public class PlayerManager {
    //region Accessors
    public int getPlayersAmount() {
        return PlayersAmount;
    }
    public int getCurrentPlayer() {
        return currentPlayer;
    }
    //endregion

    private int PlayersAmount;
    private int currentPlayer;

    public PlayerManager(int PlayersAmount) {
        this.PlayersAmount = PlayersAmount;
        currentPlayer = 1;
    }

    public int GoToNextPlayer() {
        if (currentPlayer + 1 <= PlayersAmount)
            currentPlayer++;
        else
            currentPlayer = 1;

        return currentPlayer;
    }

    public int FindNextPlayer() {
        int nextPlayer = currentPlayer;
        if (nextPlayer + 1 <= PlayersAmount)
            nextPlayer++;
        else
            nextPlayer = 1;

        return nextPlayer;
    }

}
