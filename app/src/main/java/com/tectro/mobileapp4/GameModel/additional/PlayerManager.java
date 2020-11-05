package com.tectro.mobileapp4.GameModel.additional;

import java.util.ArrayList;

public class PlayerManager {
    //region Accessors
    public int getPlayersAmount() { return players.size(); }
    //public int getIndex(Player pl) { return players.indexOf(pl); }
    public Player GetCurrent() {
        return currentPlayer;
    }
    //endregion

    private ArrayList<Player> players;
    private Player currentPlayer;

    public PlayerManager(int PlayersAmount) {
        players = new ArrayList<>();
        for (int i = 0; i < PlayersAmount; i++)
            players.add(new Player(this,()->players));

        currentPlayer = players.get(0);
    }

    public Player ToNext() {
        currentPlayer = GetNext();
        return currentPlayer;
    }

    public Player GetNext() {
        Player nextPlayer = null;

        if(players.size()>0) {
            int currentIndex = players.indexOf(currentPlayer);
            if (currentIndex + 1 < players.size())
                nextPlayer = players.get(currentIndex + 1);
            else
                nextPlayer = players.get(0);
        }

        return nextPlayer;
    }

}
