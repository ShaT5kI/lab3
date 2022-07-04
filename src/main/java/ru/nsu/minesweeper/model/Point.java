package ru.nsu.minesweeper.model;

import static java.lang.Integer.signum;
import static java.lang.Math.abs;

public class Point {
    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point plus(int x, int y) {
        return new Point(x + this.x, y + this.y);
    }

    public Point plusPoint(Point point) {
        return new Point(x + point.x, y + point.y);
    }

    public Point div(int num) {
        return new Point(x / num, y / num);
    }

    public boolean opposite(Point point) {
        return signum(x * point.x + y * point.y) < 0;
    }

    public boolean inSquare(Point point, int length) {
        return abs(x - point.x) <= length && abs(y - point.y) <= length;
    }

    @Override
    public String toString() {
        return x + " " + y;
    }
}
