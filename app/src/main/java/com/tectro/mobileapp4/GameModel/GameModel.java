package com.tectro.mobileapp4.GameModel;

import android.graphics.Point;

import com.tectro.mobileapp4.FigureModifiers.ColorEnum;
import com.tectro.mobileapp4.FigureModifiers.HeightEnum;
import com.tectro.mobileapp4.FigureModifiers.MarkEnum;
import com.tectro.mobileapp4.FigureModifiers.ShapeEnum;
import com.tectro.mobileapp4.GameModel.additional.Cell;
import com.tectro.mobileapp4.GameModel.additional.DrawHelper;
import com.tectro.mobileapp4.GameModel.additional.Figure;
import com.tectro.mobileapp4.GameModel.additional.Player;
import com.tectro.mobileapp4.GameModel.additional.PlayerManager;
import com.tectro.mobileapp4.GameModel.additional.SuitableLine;
import com.tectro.mobileapp4.GameModel.additional.SuitableLineType;
import com.tectro.mobileapp4.GameModel.additional.Winner;

import java.util.ArrayList;
import java.util.Arrays;
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
    //endregion

    //region Accessors
    public Figure getRemainingFigure(int pos) {
        if (pos >= 0 && pos < remainingFigures.size())
            return remainingFigures.get(pos);
        return null;
    }

    public int getRemainingFiguresCount() {
        return remainingFigures.size();
    }

    public Cell getTableFigure(int y, int x) {
        return tableFigures[y][x];
    }

    public Cell getTableFigure(int position) {
        int y = position / MatrixSize;
        int x = position % MatrixSize;
        return tableFigures[y][x];
    }

    public boolean setTableFigure(int position, Cell c) {
        int y = position / MatrixSize;
        int x = position % MatrixSize;
        if (x >= 0 && x < MatrixSize && y >= 0 && y < MatrixSize) {
            tableFigures[y][x] = c;
            return true;
        } else
            return false;
    }

    public int getMaxPlayers() {
        return playerManager.getPlayersAmount();
    }

    public DrawHelper getDHelper() {
        return DHelper;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }


    //endregion

    //region Constructor
    private GameModel(int playersAmount) {
        Random rand = new Random();

        playerManager = new PlayerManager(playersAmount);
        DHelper = new DrawHelper(400, playersAmount);
        MatrixSize = 4;

        remainingFigures = new ArrayList<>();
        for (ColorEnum color : ColorEnum.values())
            for (HeightEnum height : HeightEnum.values())
                for (MarkEnum mark : MarkEnum.values())
                    for (ShapeEnum shape : ShapeEnum.values())
                        remainingFigures.add(new Figure(color, height, mark, shape));

        tableFigures = new Cell[MatrixSize][];
        for (int i = 0; i < MatrixSize; i++)
            tableFigures[i] = new Cell[MatrixSize];

        SetFigureToCurrent(remainingFigures.get(rand.nextInt(remainingFigures.size())));
/*

        for (int y = 0; y < MatrixSize; y++) {
            for (int x = 0; x < MatrixSize; x++) {

                playerManager.GetCurrent().setFigureToPlace(remainingFigures.get(0));
                PutFigure(x, y);
                playerManager.ToNext();

            }
        }
*/
    }
    //endregion

    private DrawHelper DHelper;
    private final PlayerManager playerManager;

    public final int MatrixSize;
    private Cell[][] tableFigures;
    private ArrayList<Figure> remainingFigures;

    public void SetFigureToCurrent(Figure f) {
        playerManager.GetCurrent().setFigureToPlace(f);
        remainingFigures.remove(f);
    }

    public void SetFigureToNext(Figure f) {
        playerManager.GetCurrent().setFigureToPlaceNext(f);
        remainingFigures.remove(f);
    }

    public Point[] PutFigureToGame(int position) {
        Point pos = ConvertPosition(position);
        return PutFigureToGame(pos.x, pos.y);
    }

    public Point[] PutFigureToGame(int x, int y) {
        if (x >= 0 && x < MatrixSize && y >= 0 && y < MatrixSize) {

            Point Existing = GetFigureIndexes(playerManager.GetCurrent().GetCell());
            if (tableFigures[y][x] == null) {
                if (Existing == null) {
                    tableFigures[y][x] = playerManager.GetCurrent().GetCell();
                    return new Point[]{new Point(x, y)};
                } else {
                    if (tableFigures[y][x] != tableFigures[Existing.y][Existing.x]) {
                        tableFigures[y][x] = tableFigures[Existing.y][Existing.x];
                        tableFigures[Existing.y][Existing.x] = null;
                        return new Point[]{new Point(x, y), Existing};
                    }
                }
            }
        }
        return new Point[0];
    }

    private Point GetFigureIndexes(Cell tableCell) {
        for (int y = 0; y < MatrixSize; y++)
            for (int x = 0; x < MatrixSize; x++)
                if (tableFigures[y][x] != null)
                    if (tableFigures[y][x].equals(tableCell))
                        return new Point(x, y);

        return null;
    }

    public void NextRound() {
        if (CanGoToNextRound()) {
            playerManager.GetCurrent().setFigureToPlace(null);
            SetFigureToNext(playerManager.GetCurrent().getFigureToPlaceNext());
            playerManager.ToNext();
        }
    }

    public boolean CanGoToNextRound() {
        boolean result = false;
        Player cur = playerManager.GetCurrent();
        if (GetFigureIndexes(cur.GetCell()) != null && cur.getFigureToPlaceNext() != null)
            result = true;
        return result;
    }

    private List<SuitableLine> GetSuitableLines() {
        List<SuitableLine> result = new ArrayList<>();

        for (int y = 0; y < MatrixSize; y++) {
            Player PlayerVertical = null;
            boolean VerticalError = false;
            Player PlayerHorizontal = null;
            boolean HorizontalError = false;
            for (int x = 0; x < MatrixSize; x++) {
                if (tableFigures[x][y] != null) {
                    if (PlayerHorizontal == null)
                        PlayerHorizontal = tableFigures[x][y].getOwner();
                    else if (PlayerHorizontal != tableFigures[x][y].getOwner())
                        VerticalError = true;
                } else
                    VerticalError = true;

                if (tableFigures[y][x] != null) {
                    if (PlayerVertical == null)
                        PlayerVertical = tableFigures[y][x].getOwner();
                    else if (PlayerVertical != tableFigures[y][x].getOwner())
                        HorizontalError = true;
                } else HorizontalError = true;
            }

            if (!HorizontalError) {
                SuitableLine line = new SuitableLine(SuitableLineType.Horizontal, y);
                for (int x = 0; x < MatrixSize; x++)
                    line.Cells.add(tableFigures[y][x]);
                result.add(line);
            }

            if (!VerticalError) {
                SuitableLine line = new SuitableLine(SuitableLineType.Vertical, y);
                for (int x = 0; x < MatrixSize; x++)
                    line.Cells.add(tableFigures[x][y]);
                result.add(line);
            }
        }


        Player PlayerMain = null;
        boolean MainError = false;

        Player PlayerOther = null;
        boolean OtherError = false;

        for (int i = 0; i < MatrixSize; i++) {
            for (int j = 0; j < MatrixSize; j++) {
                if (i == j) {
                    if (tableFigures[i][j] != null) {
                        if (PlayerMain == null)
                            PlayerMain = tableFigures[i][j].getOwner();
                        else if (PlayerMain != tableFigures[i][j].getOwner())
                            MainError = true;
                    } else MainError = true;
                }

                if (i + j == MatrixSize - 1) {
                    if (tableFigures[i][j] != null) {
                        if (PlayerOther == null)
                            PlayerOther = tableFigures[i][j].getOwner();
                        else if (PlayerOther != tableFigures[i][j].getOwner())
                            OtherError = true;
                    } else OtherError = true;
                }
            }
        }
        if (!MainError) {
            SuitableLine line = new SuitableLine(SuitableLineType.Diagonal, 0);
            for (int i = 0; i < MatrixSize; i++)
                for (int j = 0; j < MatrixSize; j++)
                    if (i == j)
                        line.Cells.add(tableFigures[i][j]);
            result.add(line);
        }

        if (!OtherError) {
            SuitableLine line = new SuitableLine(SuitableLineType.Diagonal, 1);
            for (int i = 0; i < MatrixSize; i++)
                for (int j = 0; j < MatrixSize; j++)
                    if (i + j == MatrixSize - 1)
                        line.Cells.add(tableFigures[i][j]);
            result.add(line);
        }

        return result;
    }

    private SuitableLine CalculateResults() {
        SuitableLine result = null;

        List<SuitableLine> preLists = GetSuitableLines();

        for (SuitableLine line : preLists) {
            ColorEnum clr = null;
            HeightEnum hgt = null;
            MarkEnum mrk = null;
            ShapeEnum shp = null;

            boolean clrErr = false;
            boolean hgtErr = false;
            boolean mrkErr = false;
            boolean shpErr = false;

            Player player = null;

            for (Cell item : line.Cells) {
                player = item.getOwner();

                if (clr == null)
                    clr = item.getFigure().getColor();
                else if (clr != item.getFigure().getColor())
                    clrErr = true;

                if (hgt == null)
                    hgt = item.getFigure().getHeight();
                else if (hgt != item.getFigure().getHeight())
                    hgtErr = true;

                if (mrk == null)
                    mrk = item.getFigure().getMark();
                else if (mrk != item.getFigure().getMark())
                    mrkErr = true;

                if (shp == null)
                    shp = item.getFigure().getShape();
                else if (shp != item.getFigure().getShape())
                    shpErr = true;
            }

            if (!clrErr || !hgtErr || !mrkErr || !shpErr)
                result = line;
        }
        return result;
    }

    public Winner GetWinner() {
        SuitableLine line = CalculateResults();
        if (line != null)
            return new Winner(getPlayerManager().GetCurrent(), line);
        return null;
    }

    //region Helpers
    public Point ConvertPosition(int position) {
        int y = position / MatrixSize;
        int x = position % MatrixSize;
        return new Point(x, y);
    }

    public int ConvertPosition(int x, int y) {
        return y * MatrixSize + x;
    }
    //endregion
}
