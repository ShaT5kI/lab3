package ru.nsu.minesweeper.model;

public class Field {
    private final int size;
    private final int bombs;
    private boolean start;
    private boolean lose;
    private int opened;
    private int flags;
    private final Character [][] bombMap;
    private final Character [][] userView;
    private final boolean [][] openCells;

    public Field(GameIdentifier descriptor) {
        size = descriptor.size();
        bombs = descriptor.bombs();
        flags = bombs;

        bombMap = new Character[size][size];
        userView = new Character[size][size];
        openCells = new boolean[size][size];

        start = false;
        lose = false;
        opened = 0;

        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++) {
                userView[i][j] = 'x';
                bombMap[i][j] = '0';
                openCells[i][j] = false;
            }
        }
    }

    public Updater getView() {
        if (isWin()) {
            return new Updater(bombMap, flags);

        } else if (lose) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (bombMap[i][j] == 'b') {
                        userView[i][j] = 'b';
                    }
                }
            }
            return new Updater(userView, flags);

        } else {
            return new Updater(userView, flags);
        }
    }

    public void openSell(Point point) {
        try {
            if (!openCells[point.x][point.y]) {
                if (userView[point.x][point.y] == 'f') flags++;
                userView[point.x][point.y] = bombMap[point.x][point.y];
                openCells[point.x][point.y] = true;
                opened++;
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {

        }
    }

    public void setStart() {
        start = true;
    }

    public void setLose() {
        lose = true;
    }

    public void setFlag(Point point) {
        if (userView[point.x][point.y] != 'x') return;

        if (flags == 0) return;
        userView[point.x][point.y] = 'f';
        flags--;
    }

    public void removeFlag(Point point) {
        if (userView[point.x][point.y] != 'f') return;

        userView[point.x][point.y] = 'x';
        flags++;
    }

    public boolean isMine(Point point) {
        try {
            return bombMap[point.x][point.y] == 'b';
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    public boolean isFlag(Point point) {
        try {
            return userView[point.x][point.y] == 'f';
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    public boolean isEmpty(Point point) {
        try {
            return bombMap[point.x][point.y] == '0';
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    public boolean isNearMine(Point point) {
        try {
            return !(isMine(point) || isEmpty(point));
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    public boolean outOf(Point point) {
        return point.x >= size || point.y >= size || point.x < 0 || point.y < 0;
    }

    public boolean isOpen(Point point) {
        try {
            return openCells[point.x][point.y];
        } catch (ArrayIndexOutOfBoundsException e) {
            return true;
        }
    }

    public boolean isStart() {
        return start;
    }

    public boolean isWin() {
        return opened == (size * size - bombs);
    }

    public boolean isLose() {
        return lose;
    }

    public void setBomb(Point point) {
        bombMap[point.x][point.y] = 'b';
    }

    public void setNum(Point point) {
        try {
            if (bombMap[point.x][point.y] != 'b')
                bombMap[point.x][point.y]++;
        } catch (ArrayIndexOutOfBoundsException e) {
            //
        }
    }
}
