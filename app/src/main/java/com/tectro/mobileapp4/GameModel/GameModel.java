package com.tectro.mobileapp4.GameModel;

import com.tectro.mobileapp4.FigureModifiers.ColorEnum;
import com.tectro.mobileapp4.FigureModifiers.HeightEnum;
import com.tectro.mobileapp4.FigureModifiers.MarkEnum;
import com.tectro.mobileapp4.FigureModifiers.ShapeEnum;
import com.tectro.mobileapp4.GameModel.additional.DrawHelper;
import com.tectro.mobileapp4.GameModel.additional.Figure;
import com.tectro.mobileapp4.GameModel.additional.PlayerManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameModel {
    //region Singleton
    private static GameModel current;

    public static GameModel CreateInstance(int playersAmount) {
        if (current == null) current = new GameModel(playersAmount);
        return current;
    }
    public static GameModel GetInstance() {
        return current;
    }

    private GameModel(int playersAmount) {
        DHelper = new DrawHelper(400,playersAmount);
        playerManager = new PlayerManager(playersAmount);
        TableSize = 4;

        remainingFigures = new ArrayList<>();
        for (ColorEnum color : ColorEnum.values())
            for (HeightEnum height : HeightEnum.values())
                for (MarkEnum mark : MarkEnum.values())
                    for (ShapeEnum shape : ShapeEnum.values())
                        remainingFigures.add(new Figure(color, height, mark, shape));

        tableFigures = new FigureOwner[TableSize][];
        for (int i = 0; i < TableSize; i++)
            tableFigures[i] = new FigureOwner[TableSize];


        for (int i = 0; i < TableSize; i++) {
            for (int j = 0; j < TableSize; j++) {
                SelectedFigure = new FigureOwner(remainingFigures.get(0),0);
               // if(i<j)
                PutFigure(i,j);
            }
        }

    }

    //endregion

    //region Accessors
    public ArrayList<Figure> getRemainingFigures() {
        return remainingFigures;
    }

    public FigureOwner[][] getTableFigures() {
        return tableFigures;
    }
    public int getMaxPlayers()
    {
        return playerManager.getPlayersAmount();
    }

    public DrawHelper getDHelper() {
        return DHelper;
    }
    //endregion

    private DrawHelper DHelper;

    private final PlayerManager playerManager;

    public final int TableSize;

    private ArrayList<Figure> remainingFigures;

    private FigureOwner[][] tableFigures;

    FigureOwner SelectedFigure = null;

    public boolean PutFigure(int x, int y) {
        if (x >= 0 && x < TableSize && y >= 0 && y < TableSize && SelectedFigure != null ) {
            remainingFigures.remove(SelectedFigure.figure);
            tableFigures[x][y] = SelectedFigure;

            SelectedFigure = null;

            return true;
        } else
            return false;
    }

    public void SelectFigure(Figure figure) {
        SelectedFigure = new FigureOwner(figure, playerManager.FindNextPlayer());
    }



    private List<List<FigureOwner>> GetSuitableLines() {
        List<List<FigureOwner>> result = new ArrayList<>();

        for (int i = 0; i < TableSize; i++) {
            int PlayerVertical = 0;
            boolean VerticalError = false;
            int PlayerHorizontal = 0;
            boolean HorizontalError = false;
            for (int j = 0; j < TableSize; j++) {
                if (PlayerHorizontal == 0)
                    PlayerHorizontal = tableFigures[i][j].player;
                else if (PlayerHorizontal != tableFigures[i][j].player)
                    VerticalError = true;


                if (PlayerVertical == 0)
                    PlayerVertical = tableFigures[j][i].player;
                else if (PlayerVertical != tableFigures[j][i].player)
                    HorizontalError = true;
            }

            if (!HorizontalError) {
                List<FigureOwner> resultLine = new ArrayList<>();
                for (int j = 0; j < TableSize; j++)
                    resultLine.add(tableFigures[i][j]);
                result.add(resultLine);
            }

            if (!VerticalError) {
                List<FigureOwner> resultLine = new ArrayList<>();
                for (int j = 0; j < TableSize; j++)
                    resultLine.add(tableFigures[j][i]);
                result.add(resultLine);
            }
        }


        int PlayerMain = 0;
        boolean MainError = false;
        int PlayerOther = 0;
        boolean OtherError = false;
        for (int i = 0; i < TableSize; i++) {
            for (int j = 0; j < TableSize; j++) {
                if (i == j) {
                    if (PlayerMain == 0)
                        PlayerMain = tableFigures[i][j].player;
                    else if (PlayerMain != tableFigures[i][j].player)
                        MainError = true;
                }
                if (i + j == TableSize - 1) {
                    if (PlayerOther == 0)
                        PlayerOther = tableFigures[i][j].player;
                    else if (PlayerOther != tableFigures[i][j].player)
                        OtherError = true;
                }
            }
        }
        if (!MainError) {
            List<FigureOwner> resultLine = new ArrayList<>();
            for (int i = 0; i < TableSize; i++)
                for (int j = 0; j < TableSize; j++)
                    if (i == j)
                        resultLine.add(tableFigures[i][j]);
            result.add(resultLine);
        }

        if (!OtherError) {
            List<FigureOwner> resultLine = new ArrayList<>();
            for (int i = 0; i < TableSize; i++)
                for (int j = 0; j < TableSize; j++)
                    if (i + j == TableSize - 1)
                        resultLine.add(tableFigures[i][j]);
            result.add(resultLine);
        }

        return result;
    }

    public int CalculateResults() {
        List<List<FigureOwner>> preLists = GetSuitableLines();

        for (List<FigureOwner> line : preLists) {
            ColorEnum clr = null;
            HeightEnum hgt = null;
            MarkEnum mrk = null;
            ShapeEnum shp = null;

            boolean clrErr = false;
            boolean hgtErr = false;
            boolean mrkErr = false;
            boolean shpErr = false;

            int player = 0;

            for (FigureOwner item : line) {
                player = item.player;

                if (clr == null)
                    clr = item.figure.getColor();
                else if (clr != item.figure.getColor())
                    clrErr = true;

                if (hgt == null)
                    hgt = item.figure.getHeight();
                else if (hgt != item.figure.getHeight())
                    hgtErr = true;

                if (mrk == null)
                    mrk = item.figure.getMark();
                else if (mrk != item.figure.getMark())
                    mrkErr = true;

                if (shp == null)
                    shp = item.figure.getShape();
                else if (shp != item.figure.getShape())
                    shpErr = true;
            }

            if (!clrErr || !hgtErr || !mrkErr || !shpErr){
                playerManager.GoToNextPlayer();
                return player;}
        }
        playerManager.GoToNextPlayer();
        return 0;
    }

    public class FigureOwner {
        //region Accessors
        public Figure getFigure() {
            return figure;
        }

        public int getPlayer() {
            return player;
        }
        //endregion

        private final Figure figure;
        private final int player;

        FigureOwner(Figure figure, int player) {
            this.figure = figure;
            this.player = player;
        }
    }
}
