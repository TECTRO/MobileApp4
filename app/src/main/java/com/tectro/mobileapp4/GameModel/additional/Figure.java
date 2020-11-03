package com.tectro.mobileapp4.GameModel.additional;

import com.tectro.mobileapp4.FigureModifiers.ColorEnum;
import com.tectro.mobileapp4.FigureModifiers.HeightEnum;
import com.tectro.mobileapp4.FigureModifiers.MarkEnum;
import com.tectro.mobileapp4.FigureModifiers.ShapeEnum;

public class Figure {
    public Figure(ColorEnum color, HeightEnum height, MarkEnum mark, ShapeEnum shape) {
        this.color = color;
        this.height = height;
        this.mark = mark;
        this.shape = shape;
    }

    //region Accessors
    public ColorEnum getColor() {
        return color;
    }

    public HeightEnum getHeight() {
        return height;
    }

    public MarkEnum getMark() {
        return mark;
    }

    public ShapeEnum getShape() {
        return shape;
    }
    //endregion

    private final ColorEnum color;
    private final HeightEnum height;
    private final MarkEnum mark;
    private final ShapeEnum shape;
}
