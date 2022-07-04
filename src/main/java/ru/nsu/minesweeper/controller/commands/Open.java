package ru.nsu.minesweeper.controller.commands;

import ru.nsu.minesweeper.exceptions.ClassExistenceException;
import ru.nsu.minesweeper.model.*;

import java.util.HashMap;
import java.util.Map;

public class Open extends AbstractGameCommand {
    public Open(CommandIdentifier descriptor) throws ClassExistenceException {
        super(descriptor);
    }

    private static final Map<String, Point> vectors = new HashMap<>() {
        {
            put("up", new Point(0, -1));
            put("down", new Point(0, 1));
            put("right", new Point(1, 0));
            put("left", new Point(-1, 0));

            put("downRight", new Point(1, 1));
            put("upLeft", new Point(-1, -1));
            put("upRight", new Point(1, -1));
            put("downLeft", new Point(-1, 1));
        }
    };


    @Override
    public Marks run() throws ClassExistenceException {
        if (field.isMine(point)) {
            field.setLose();
            return Marks.False;
        }

        openMain(point);
        return Marks.True;
    }

    public void openMain(Point point) throws ClassExistenceException {
        if (field.outOf(point)) {
            throw new ClassExistenceException();
        }

        if (field.isNearMine(point)) {
            field.openSell(point);
            return;
        }

        if (field.isMine(point)) {
            return;
        }

        if (field.isEmpty(point)) {
            field.openSell(point);
            openSide(point.plus(0, -1), "up");
            openSide(point.plus(-1, 0), "left");
            openSide(point.plus(1, 0), "right");
            openSide(point.plus(0, 1), "down");

            openSide(point.plus(1, 1), "downRight");
            openSide(point.plus(-1, -1), "upLeft");
            openSide(point.plus(1, -1), "upRight");
            openSide(point.plus(-1, 1), "downLeft");
        }
    }

    private void openSide(Point point, String vector) {
        if (field.isOpen(point) || field.isMine(point) || field.outOf(point)) {
            return;
        }

        if (field.isNearMine(point)) {
            field.openSell(point);
            return;
        }

        field.openSell(point);
        for (var i : vectors.entrySet()) {
            if (!i.getValue().opposite(vectors.get(vector))) {
                openSide(point.plusPoint(i.getValue()), i.getKey());
            }
        }

    }
}