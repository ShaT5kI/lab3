package ru.nsu.minesweeper.view;

import ru.nsu.minesweeper.model.Updater;

public class ConsoleView implements View {
    private static long startTime;

    @Override
    public void startGame() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public void endGame() {

    }

    @Override
    public void showGreetScreen() {
        System.out.println("1.New Game");
        System.out.println("2.High scores");
        System.out.println("3.About");
        System.out.println("4.Exit");
    }

    @Override
    public void update(Updater data, long time) {
        showTime((time + startTime) - System.currentTimeMillis());
        printMap(data.view(), data.view().length);
    }

    @Override
    public void showList(String message) {
        System.out.println(message);
    }

    @Override
    public void showText(String message) {
        System.out.println(message);
    }

    @Override
    public void showWarning(String message) {
        showList(message);
    }

    @Override
    public void askUser(String message) {
        showList(message);
    }

    public static void printMap(Object[][] field, int size) {
        System.out.print(" " + "|");
        for (int i = 0; i < size; i++) {
            System.out.print(i % 10 + " ");
        }
        System.out.println();
        for (int i = 0; i < size; i++) {
            System.out.print("_ ");
        }
        System.out.println("_");

        for (int i = 0; i < size; i++) {
            System.out.print(i % 10 + "|");
            for (int j = 0; j < size; j++) {
                System.out.print(field[j][i] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }


    @Override
    public void setAlreadyCreated(boolean alreadyCreated) {
    }

    public void showTime(long time) {
        String output = String.format("%tM:%tS", time, time);
        System.out.println(output);
    }

    @Override
    public String waitAction() {
        return null;
    }
}
