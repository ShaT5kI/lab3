package ru.nsu.minesweeper.model;

import java.io.IOException;
import java.util.HashSet;
import java.util.Random;

public class MapCreator {
    protected final Field field;
    protected final int size;
    protected final int bombs;
    protected final int safetyRad;
    private final int lab;
    private HashSet<String> labyrinth;

    public MapCreator(GameIdentifier descriptor, Field field) {
        this.field = field;
        size = descriptor.size();
        bombs = descriptor.bombs();
        safetyRad = descriptor.safetyRad();
        lab = descriptor.labyrinth();
    }

    public void initField(Point start) throws IOException {
        if (!(start.x >= 0 && start.y >= 0 && start.x < size && start.y < size))
            throw new IOException("incorrect point");

        if (lab >= 1) {
            var labCreator = new LabyrinthCreator(size, field, start);
            labyrinth = labCreator.getLabyrinth();
        }
        else labyrinth = new HashSet<>(){{add(" ");}};

        final Random random = new Random();

        for (int i = 0; i < bombs; i++) {
            var point = randPoint(random, start);
            field.setBomb(point);
            fillNeighbours(point);
        }
    }

    public void fillNeighbours(Point point) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (!(j == 0 && i == 0))
                    field.setNum(point.plus(i, j));
            }
        }
    }

    boolean isBad(Point point, Point start) {
        return field.isMine(point) || point.inSquare(start, safetyRad) || labyrinth.contains(point.toString());
    }

    Point randPoint (Random random, Point start) {
        int x, y;

        Point point;
        do {
            x = random.nextInt(size - 1);
            y = random.nextInt(size - 1);
            point = new Point(x, y);
        } while (isBad(point, start));

        return point;
    }
}
