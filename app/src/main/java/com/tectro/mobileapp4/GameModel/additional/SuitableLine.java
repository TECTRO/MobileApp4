package com.tectro.mobileapp4.GameModel.additional;

import com.tectro.mobileapp4.GameModel.GameModel;

import java.util.ArrayList;

public class SuitableLine {
    //region Accessors
    public SuitableLineType getLineType() {
        return LineType;
    }

    public ArrayList<Cell> getCells() {
        return Cells;
    }

    public int getLineIndex() {
        return LineIndex;
    }
    //endregion

    //region Constructor
    public SuitableLine(SuitableLineType lineType, int lineIndex) {
        LineType = lineType;
        LineIndex = lineIndex;
        Cells = new ArrayList<>();
    }
    //endregion

    private final SuitableLineType LineType;
    private final int LineIndex;
    public final ArrayList<Cell> Cells;
}