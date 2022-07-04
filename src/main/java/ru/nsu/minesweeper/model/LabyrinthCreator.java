package ru.nsu.minesweeper.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

public class LabyrinthCreator {
    HashSet<String> labyrinth;
    protected final Field field;
    protected final int size;

    public LabyrinthCreator(int size, Field field, Point start) {
        this.field = field;
        this.size = size;

        Random random = new Random();
        labyrinth = new HashSet<>();
        createLabyrinth(random, start);
    }

    private static final Map<Integer, Point> vectors = new HashMap<>() {
        {
            put(1, new Point(0, -2));
            put(2, new Point(0, 2));
            put(3, new Point(2, 0));
            put(4, new Point(-2, 0));
        }
    };

    public HashSet<String> getLabyrinth() {
        return labyrinth;
    }

    public void createLabyrinth(Random random, Point start) {
        int key;
        Point vector;
        HashSet<Integer> set = new HashSet<>();

        labyrinth.add(start.toString());
        while (set.size() < 4) {
            do {
                key = random.nextInt(1, 5);
            } while (set.contains(key));
            set.add(key);
            vector = vectors.get(key);
            Point newP = start.plusPoint(vector);

            if (free(newP, labyrinth)) {
                labyrinth.add(start.plusPoint(vector).toString());
                labyrinth.add(start.plusPoint(vector.div(2)).toString());
                createLabyrinth(random, newP);
            }
        }
    }

    boolean free(Point newP, HashSet<String> labyrinth) {
        if (labyrinth.contains(newP.toString())) {
            return false;
        }
        return newP.x >= 0 && newP.y >= 0 && newP.x < size && newP.y < size;
    }
}
